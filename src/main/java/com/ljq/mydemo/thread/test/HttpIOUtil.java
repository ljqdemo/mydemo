package com.ljq.mydemo.thread.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
/**
 * File
 */
public class HttpIOUtil {
    private static Logger log = LoggerFactory.getLogger(HttpIOUtil.class);
    private final static String BOUNDARY = UUID.randomUUID().toString()
            .toLowerCase().replaceAll("-", "");// 边界标识
    private final static String PREFIX = "--";// 必须存在
    private final static String LINE_END = "\r\n";

    /**
     *  POST Multipart Request
     *  @Description:
     *  @param requestUrl 请求url
     *  @param requestText 请求参数
     *  @param requestFile 请求上传的文件
     *  @return
     *  @throws Exception
     */
    public static String sendRequest(String requestUrl,
                                     Map<String, Object> requestText, Map<String, File> requestFile) throws Exception{
        HttpURLConnection conn = null;
        InputStream input = null;
        OutputStream os = null;
        BufferedReader br = null;
        StringBuffer buffer = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(1000 * 10);
            conn.setReadTimeout(1000 * 10);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.connect();

            // 往服务器端写内容 也就是发起http请求需要带的参数
            os = new DataOutputStream(conn.getOutputStream());
            // 请求参数部分
            writeParams(requestText, os);
            // 请求上传文件部分
            writeFile(requestFile, os);
            // 请求结束标志
            String endTarget = PREFIX + BOUNDARY + PREFIX + LINE_END;
            os.write(endTarget.getBytes());
            os.flush();

            // 读取服务器端返回的内容
            System.out.println("======================响应体=========================");
            System.out.println("ResponseCode:" + conn.getResponseCode()
                    + ",ResponseMessage:" + conn.getResponseMessage());
            if(conn.getResponseCode()==200){
                input = conn.getInputStream();
            }else{
                input = conn.getErrorStream();
            }

            br = new BufferedReader(new InputStreamReader( input, "UTF-8"));
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            //......
            System.out.println("返回报文:" + buffer.toString());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e);
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                    conn = null;
                }

                if (os != null) {
                    os.close();
                    os = null;
                }

                if (br != null) {
                    br.close();
                    br = null;
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
                throw new Exception(ex);
            }
        }
        return buffer.toString();
    }

    /**
     * 对post参数进行编码处理并写入数据流中
     * @throws Exception
     *
     * @throws IOException
     *
     * */
    private static void writeParams(Map<String, Object> requestText,
                                    OutputStream os) throws Exception {
        try{
            String msg = "请求参数部分:\n";
            if (requestText == null || requestText.isEmpty()) {
                msg += "空";
            } else {
                StringBuilder requestParams = new StringBuilder();
                Set<Map.Entry<String, Object>> set = requestText.entrySet();
                Iterator<Entry<String, Object>> it = set.iterator();
                while (it.hasNext()) {
                    Entry<String, Object> entry = it.next();
                    requestParams.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    requestParams.append("Content-Disposition: form-data; name=\"")
                            .append(entry.getKey()).append("\"").append(LINE_END);
                    requestParams.append("Content-Type: text/plain; charset=utf-8")
                            .append(LINE_END);
                    requestParams.append("Content-Transfer-Encoding: 8bit").append(
                            LINE_END);
                    requestParams.append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容
                    requestParams.append(entry.getValue());
                    requestParams.append(LINE_END);
                }
                os.write(requestParams.toString().getBytes());
                os.flush();

                msg += requestParams.toString();
            }

            //System.out.println(msg);
        }catch(Exception e){
            log.error("writeParams failed", e);
            throw new Exception(e);
        }
    }

    /**
     * 对post上传的文件进行编码处理并写入数据流中
     *
     * @throws IOException
     *
     * */
    private static void writeFile(Map<String, File> requestFile,
                                  OutputStream os) throws Exception {
        InputStream is = null;
        try{
            String msg = "请求上传文件部分:\n";
            if (requestFile == null || requestFile.isEmpty()) {
                msg += "空";
            } else {
                StringBuilder requestParams = new StringBuilder();
                Set<Map.Entry<String, File>> set = requestFile.entrySet();
                Iterator<Entry<String, File>> it = set.iterator();
                while (it.hasNext()) {
                    Entry<String, File> entry = it.next();
                    if(entry.getValue() == null){//剔除value为空的键值对
                        continue;
                    }
                    requestParams.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    requestParams.append("Content-Disposition: form-data; name=\"")
                            .append(entry.getKey()).append("\"; filename=\"")
                            .append(entry.getValue().getName()).append("\"")
                            .append(LINE_END);
                    requestParams.append("Content-Type:")
                            .append(getContentType(entry.getValue()))
                            .append(LINE_END);
                    requestParams.append("Content-Transfer-Encoding: 8bit").append(
                            LINE_END);
                    requestParams.append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容

                    os.write(requestParams.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(entry.getValue()));
                    RandomAccessFile raf = new RandomAccessFile(entry.getValue(), "rw");
                   //设置断点续传的位置
                    raf.seek(2000);

                    byte[] bufferOut = new byte[200];
                    int bytes = 0;
                    int counter = 0;
                    // 每次读1KB数据,并且将文件数据写入到输出流中
                    while ((bytes = raf.read(bufferOut)) != -1) {
                        os.write(bufferOut, 0, bytes);
                       /* if (counter++ >3){
                            break;
                        }*/
                    }

                  //  os.write(in);

                 /*   byte[] bufferOut = new byte[200];
                    int bytes = 0;
                    int counter = 0;
                    // 每次读1KB数据,并且将文件数据写入到输出流中
                    while ((bytes = in.read(bufferOut)) != -1) {
                        os.write(bufferOut, 0, bytes);
                        if (counter++ >3){
                            break;
                        }
                    }*/
                    os.write(LINE_END.getBytes());
                    os.flush();

                    msg += requestParams.toString();
                }
            }
            //System.out.println(msg);
        }catch(Exception e){
            log.error("writeFile failed", e);
            throw new Exception(e);
        }finally{
            try{
                if(is!=null){
                    is.close();
                }
            }catch(Exception e){
                log.error("writeFile FileInputStream close failed", e);
                throw new Exception(e);
            }
        }
    }

    /**
     * ContentType(这部分可忽略，File有对应的方法获取)
     *
     * @Description:
     * @param file
     * @return
     * @throws IOException
     */
    public static String getContentType(File file) throws Exception{
        String streamContentType = "application/octet-stream";
        String imageContentType = "";
        ImageInputStream image = null;
        try {
            image = ImageIO.createImageInputStream(file);
            if (image == null) {
                return streamContentType;
            }
            Iterator<ImageReader> it = ImageIO.getImageReaders(image);
            if (it.hasNext()) {
                imageContentType = "image/" + it.next().getFormatName();
                return imageContentType;
            }
        } catch (IOException e) {
            log.error("method getContentType failed", e);
            throw new Exception(e);
        } finally {
            try{
                if (image != null) {
                    image.close();
                }
            }catch(IOException e){
                log.error("ImageInputStream close failed", e);;
                throw new Exception(e);
            }

        }
        return streamContentType;
    }
    //MockFile是单元测试的类，故需要单元测试的包
    //ContentType是org.apache.http.entity.ContentType
    //ContentType.APPLICATION_OCTET_STREAM.toString()可用字符串application/octet-stream代替
    public static void main(String[] args) throws Exception {
        String requestURL = "http://192.168.1.145:21122/rest-api/v1/upload/append";
        HttpIOUtil httpReuqest = new HttpIOUtil();

        Map<String,Object> requestText = new HashMap<String,Object>();
        requestText.put("append", true);
        requestText.put("checksum", "6cdbfc28e1b00c9ffeaa400fb46de715");
        requestText.put("filesize", 51599584);
        requestText.put("cmdid", 2);
        requestText.put("filename", "test.mkv");
        requestText.put("mac", "111111111111");
        //设置断点续传的位置
        requestText.put("position", 2000);
        requestText.put("type", 1);

        Map<String,File> requestFile = new HashMap<String,File>();
        File file = new File("D:\\22\\weather\\test.mkv");
        FileInputStream fileInputStream = new FileInputStream(file);
      /*  File File = new File(file.getName(),
                file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),
                fileInputStream);*/
        requestFile.put("file",file);
        sendRequest(requestURL, requestText, requestFile);
    }
}