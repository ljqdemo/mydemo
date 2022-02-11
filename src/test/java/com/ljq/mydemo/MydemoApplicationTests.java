package com.ljq.mydemo;

import cn.hutool.extra.spring.SpringUtil;
import com.ljq.mydemo.spring.bean.CarFactoryBean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;

@SpringBootTest
@ImportResource(locations = {"beans.xml"})
class MydemoApplicationTests {


    @Test
    void contextLoads() {
        com.ljq.mydemo.bean.Car car = SpringUtil.getBean("car");
        System.out.println(car);



        CarFactoryBean carFactoryBean = SpringUtil.getBean("&car");
        System.out.println(carFactoryBean);
    }



}
