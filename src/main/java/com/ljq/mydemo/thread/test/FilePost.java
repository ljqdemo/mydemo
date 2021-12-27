package com.ljq.mydemo.thread.test;

import com.ljq.mydemo.util.Md5;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author gino
 * 2021-12-10
 */
public class FilePost {


    public static void uploadFile(String fileName) {
        try {

            int position = 0;

            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "========7d4a6d158c9";
            // 服务器的域名
            URL url = new URL("http://192.168.1.145:21122/rest-api/v1/upload/append");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // 上传文件
            File file = new File(fileName);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(position);


            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + fileName
                    + "\"" + newLine);
            sb.append("Content-Disposition: form-data;name=\"append\";append=\"" + false
                    + "\"" + newLine);
            sb.append("Content-Disposition: form-data;name=\"checksum\";checksum=\"" + "6cdbfc28e1b00c9ffeaa400fb46de715"
                    + "\"" + newLine);
            sb.append("Content-Disposition: form-data;name=\"cmdid\";cmdid=\"" + 2
                    + "\"" + newLine);
            sb.append("Content-Disposition: form-data;name=\"filesize\";filesize=\"" + 51599584
                    + "\"" + newLine);
            sb.append("Content-Disposition: form-data;name=\"mac\";mac=\"" + "11111111111"
                    + "\"" + newLine);
            sb.append("Content-Disposition: form-data;name=\"position\";position=\"" + position
                    + "\"" + newLine);
            sb.append("Content-Disposition: form-data;name=\"type\";type=\"" + 1
                    + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 数据输入流,用于读取文件数据
//            DataInputStream in = new DataInputStream(new FileInputStream(raf));
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[200];
            int bytes = 0;
            int counter = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = raf.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
              if (counter++ >3){
                  break;
              }
            }
            // 最后添加换行
            out.write(newLine.getBytes());
//            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                    .getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String file="D:\\22\\weather\\play_list.tar.gz";
        File file1=new File(file);
        Integer c=0x10325476;
        //6cdbfc28e1b00c9ffeaa400fb46de715

        String newChecksum = new Md5(c).ComputeFileMd5(file1.getAbsolutePath());
        System.out.println(newChecksum);
        System.out.println(file1.length());
       // uploadFile(file);
    }
}
