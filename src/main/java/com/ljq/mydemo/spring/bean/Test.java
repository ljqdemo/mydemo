package com.ljq.mydemo.spring.bean;

import com.ljq.mydemo.bean.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gino
 * 2022-01-17
 */
public class Test {

    public static void main(String[] args) {

        List<com.ljq.mydemo.bean.User> users=new ArrayList<>();

        com.ljq.mydemo.bean.User zs=new com.ljq.mydemo.bean.User("zs","ID18256",18);
        com.ljq.mydemo.bean.User ls=new com.ljq.mydemo.bean.User("ls","ID18257",18);
        com.ljq.mydemo.bean.User w5=new com.ljq.mydemo.bean.User("w5","ID18258",20);
        com.ljq.mydemo.bean.User zz=new com.ljq.mydemo.bean.User("zz","ID18259",20);

        users.add(zs);
        users.add(ls);
        users.add(w5);
        users.add(zz);

        //�����������
        Map<Integer, List<User>> map = users.stream().collect(Collectors.groupingBy(User::getAge));

        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Integer age = iterator.next();
            System.out.println("分组年龄"+age);

            List<User> users1 = map.get(age);
            System.out.println("分组年龄:"+age+"对应的分组list");
            users1.forEach(e->{
                System.out.println(e.toString());
            });

        }
        

    }
}
