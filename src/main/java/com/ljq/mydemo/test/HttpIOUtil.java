package com.ljq.mydemo.test;



import com.alibaba.fastjson.JSONObject;
import com.ljq.mydemo.util.Md5;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
/**
 * File
 */
@Slf4j
public class HttpIOUtil {
    //private static log log = log.getlog(HttpIOUtil.class);
    private final static String BOUNDARY = UUID.randomUUID().toString()
            .toLowerCase().replaceAll("-", "");// 边界标识
    private final static String PREFIX = "--";// 必须存在
    private final static String LINE_END = "\r\n";

    private static String requestURL = "http://192.168.1.70:21122/rest-api/v1/upload/append";
    private static String url_upload_check = "http://192.168.1.70:21122/rest-api/v1/upload/check";



    /**
     *  POST Multipart Request
     *  @Description:
     *  @param requestUrl 请求url
     *  @param requestText 请求参数
     *  @param requestFile 请求上传的文件
     *  @return
     *  @throws Exception
     */
    public static String sendRequest(String requestUrl, Map<String, Object> requestText, Map<String, File> requestFile) throws Exception{
        HttpURLConnection conn = null;
        InputStream input = null;
        OutputStream os = null;
        BufferedReader br = null;
        StringBuffer buffer = null;
        log.info(requestUrl);
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (GlobalData.HTTP_PROXY_STATUS) {
                InetSocketAddress address = new InetSocketAddress(GlobalData.HTTP_PROXY_HOST, GlobalData.HTTP_PROXY_PORT);
                Proxy proxy = new Proxy(Proxy.Type.HTTP, address); // HTTP代理
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }

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
            writeFile(requestFile, os, Integer.parseInt(requestText.get("position").toString()) );
            // 请求结束标志
            String endTarget = PREFIX + BOUNDARY + PREFIX + LINE_END;
            os.write(endTarget.getBytes());
            os.flush();

            // 读取服务器端返回的内容
            log.info("======================响应体=========================");
            log.info("ResponseCode:" + conn.getResponseCode() + ",ResponseMessage:" + conn.getResponseMessage());
            if(conn.getResponseCode()==200){
                input = conn.getInputStream();
            }else{
                input = conn.getErrorStream();
            }

            br = new BufferedReader(new InputStreamReader( input, StandardCharsets.UTF_8));
            buffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }

            log.info("返回报文:" + buffer);

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
                Set<Entry<String, Object>> set = requestText.entrySet();
                for (Entry<String, Object> entry : set) {
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
                                  OutputStream os ,int position) throws Exception {
        InputStream is = null;
        try{
            StringBuilder msg = new StringBuilder("请求上传文件部分:\n");
            if (requestFile == null || requestFile.isEmpty()) {
                msg.append("空");
            } else {
                StringBuilder requestParams = new StringBuilder();
                Set<Entry<String, File>> set = requestFile.entrySet();
                for (Entry<String, File> entry : set) {
                    if (entry.getValue() == null) {//剔除value为空的键值对
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
//                    DataInputStream in = new DataInputStream(new FileInputStream(entry.getValue()));
                    RandomAccessFile raf = new RandomAccessFile(entry.getValue(), "rw");
                    //设置断点续传的位置
                    raf.seek(position);

                    byte[] bufferOut = new byte[10241];
                    int bytes = 0;
                    int counter = 0;
                    // 每次读1KB数据,并且将文件数据写入到输出流中
                    while ((bytes = raf.read(bufferOut)) != -1) {
                        os.write(bufferOut, 0, bytes);
                        if (position>=0 && position<=102414) {
                            if (counter++ > 100){
                                break;
                            }
                        }
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

                    msg.append(requestParams);
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


    /**
     * 文件增量续传测试
     * @param append 是否为续传
     * @param checksum  文件校验码
     * @param cmdid  命令ID
     * @param file	上传文件
     * @param mac  	MAC地址
     * @param position  文件续传位置
     * @param type  文件类型(1:系统日志 2:播放日志 3:终端截图 4:DTV节目文件)
     */
    public static synchronized  void test_upload_append(boolean append,String checksum,long cmdid,File file,String mac,int position,int type){
        try{
            Map<String,Object> requestText = new HashMap<>();
            requestText.put("append", append);
            requestText.put("checksum", checksum);
            requestText.put("filesize", file.length());
            requestText.put("filename", file.getName());
            requestText.put("cmdid", cmdid);
            requestText.put("mac", mac);
            requestText.put("position", position);
            requestText.put("type", type);

           /*   FileInputStream fileInputStream = new FileInputStream(file);
                File File = new File(file.getName(),
                file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),
                fileInputStream);*/

            Map<String,File> requestFile = new HashMap<>();
            requestFile.put("file",file);
            sendRequest(requestURL, requestText, requestFile);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String test_upload_check(String checksum,long cmdid,String fileName,long fileSize,String mac){

        HashMap<String,String> params = new HashMap<>();
        params.put("checksum", checksum);
        params.put("cmdid", cmdid + "");
        params.put("filename", fileName);
        params.put("fileSize", fileSize + "");
        params.put("mac", mac);
        log.info(params.toString());

        HashMap<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/x-www-form-urlencoded");
//        headers.put("Request-Origion","*");

        return HTTPHelper.post_map(url_upload_check,params,headers) ;
    }


    //MockFile是单元测试的类，故需要单元测试的包
    //ContentType是org.apache.http.entity.ContentType
    //ContentType.APPLICATION_OCTET_STREAM.toString()可用字符串application/octet-stream代替
    public static void main(String[] args) throws Exception {
//        requestURL = "http://124.232.158.120:9080/bj-rest-api/v1/upload/append";
//        url_upload_check = "http://124.232.158.120:9080/bj-rest-api/v1/upload/check";

        GlobalData.HTTP_PROXY_STATUS = true;

        String filePath = "D:\\22\\weather\\play_list.tar.gz";
        String mac = "111111111111";

        File file = new File(filePath);
        String checksum = new Md5(0x10325476).ComputeFileMd5(file.getAbsolutePath());
//        String newChecksum = new Md5(verifyKey).ComputeFileMd5(destFile.getAbsolutePath());
        log.info("file: " + filePath + ", checksum: " + checksum);

        int position = 0;
        long size=file.length();
        String check_result = test_upload_check(checksum, 20210002, file.getName(), file.length(), mac);
        log.info("文件校验---- " + check_result);
        JSONObject rsp_check = JSONObject.parseObject(check_result);
        position = rsp_check.getInteger("data");
        log.info("已上传：" + position);
        while(position < size) {
            if(position==size){
                break;
            }
            if (rsp_check.getInteger("code") == 200) {
                test_upload_append(position >0?true:false, checksum, 20210002, file, mac, position, 1);

             //   Thread.sleep(3000);

                 check_result = test_upload_check(checksum, 20210002, file.getName(), file.length(), mac);
                log.info("文件校验---- " + check_result);
                 rsp_check = JSONObject.parseObject(check_result);
                position = rsp_check.getInteger("data");
                log.info("已上传2：" + position);
            } else {
                log.info("文件校验失败！ " + check_result);
            }
        }
    }
}