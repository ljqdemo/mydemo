package com.ljq.mydemo.thread.compute;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gino
 * 2021-11-17
 */
public class ComputeTest002 {


    public static void main(String[] args) throws InterruptedException {
        //实例具体的计算方法
        ExampleCompute compute=new ExampleCompute();
        //实例化到计算的容器
        MemoryCompute002<String, BigInteger> memoryCompute=new MemoryCompute002(compute);

        //单线程
     //   OneThread(memoryCompute);

        //多线程
        MoreThread(memoryCompute);
    }

    /**
     * 单线程
     * @param memoryCompute
     * @throws InterruptedException
     */
    public static void OneThread( MemoryCompute002<String, BigInteger> memoryCompute) throws InterruptedException {
        for (int i = 0; i <5 ; i++) {
            System.out.println("create randomNumber: "+6);
            memoryCompute.compute(String.valueOf(6));
        }

    }

    /**
     * 多线程
     * @param memoryCompute
     * @throws InterruptedException
     */
    public static void MoreThread( MemoryCompute002<String, BigInteger> memoryCompute){
        //创建带缓存的线程池
        ExecutorService services = new ThreadPoolExecutor(2, 5,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        //模拟多线程访问
        for (int i = 0; i <5 ; i++) {

            services.execute(()->{
                System.out.println("compute args : "+6);
                try {
                    BigInteger compute = memoryCompute.compute(String.valueOf(6));
                    System.out.println(Thread.currentThread().getId()+" get result :"+compute);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //关闭数据库连接池
        services.shutdown();
    }

}
