package com.ljq.mydemo.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gino
 * 2022-01-11
 */
@Configuration
public class Config {
    @Bean("User")
    public Object testBean(){
        return new com.ljq.mydemo.bean.User("1sa","ID8888",18);
    }
}
