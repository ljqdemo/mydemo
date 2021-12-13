package com.ljq.mydemo.thread.uncaugphtExcePtionHandler;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

/**
 * @author gino
 * 2021-12-07
 */
public class a {
    public static void main(String[] args) {
        ThreadFactory build = ThreadFactoryBuilder.create().build();

        int cpu = Runtime.getRuntime().availableProcessors();
        System.out.println(cpu);

    }
}
