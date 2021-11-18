package com.ljq.mydemo.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gino
 * 2021-07-13
 * 带缓冲的线程池
 */
public class CachedThreadPoolTest {

    public static void main(String[] args) {
        //创建带缓存的线程池
        ExecutorService services = Executors.newCachedThreadPool();
        services.execute(() -> {
            System.out.println("aaas"+Thread.currentThread().getName());
        });
        services.execute(() -> {
            System.out.println("bbbb"+Thread.currentThread().getName());
        });
        services.shutdown();
    }
}
