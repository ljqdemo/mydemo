package com.ljq.mydemo.test.mytest;

import java.util.ArrayList;

/**
 * @author gino
 * 2022-02-09
 */
public class test002 {

    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");

        list.forEach(e->{
            System.out.println(e);
        });
        for (String s : list) {
            System.out.println(s);
        }
        //  == 比较对象的时候是比较地址  ， 比较基本数据类型的时候是比较的  值
        // 但是基本数据类型  如Integer   会有一个缓存池


        Integer  i=100;
        Integer i2=100;
        System.out.println(i==i2);
        System.out.println(i.equals(i2));

        Integer i3=new Integer(100);
        System.out.println("i3----i1:  == :");
        System.out.println(i3==i);
        Integer i4=new Integer(100);
        System.out.println("i3----i4:  == :");
        System.out.println(i3==i4);

        int i5=100;

        System.out.println("i3----i5:  == :");
        System.out.println(i3==i5);
        

    }
}
