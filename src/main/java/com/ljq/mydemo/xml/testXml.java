package com.ljq.mydemo.xml;

/**
 * @author gino
 * 2021-12-20
 */
public class testXml {
    private static  final String  path="D:\\11\\weather_bj.xml";

    public static void main(String[] args) {
     String key="121_80";
        String substring = key.substring(0, key.indexOf("_"));
        String substring2= key.substring( key.indexOf("_")+1);
        System.out.println(substring);
        System.out.println(substring2);

    }


}
