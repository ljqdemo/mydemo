package com.ljq.mydemo.thread.compute;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gino
 * 2021-11-17
 */
public class ComputeTest001 {


    public static void main(String[] args) throws InterruptedException {
        //实例具体的计算方法
        ExampleCompute compute=new ExampleCompute();
        //实例化到计算的容器
        MemoryCompute<String, BigInteger> memoryCompute=new MemoryCompute(compute);


        //创建带缓存的线程池
        ExecutorService services = Executors.newCachedThreadPool();
        //模拟多线程访问
        for (int i = 0; i <5 ; i++) {

                    System.out.println("create randomNumber: "+6);
                    memoryCompute.compute(String.valueOf(6));

        }
        //关闭数据库连接池
        services.shutdown();

    }

}
