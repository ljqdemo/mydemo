package com.ljq.mydemo.thread.pool.timer;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * 固定周期执行
 *
 * 正常： 此线程池为40毫秒执行一次，主线程等待5000毫秒最多执行  125次就会退出
 * 异常：当有线程抛出异常以后 线程池会停止执行，但是不会抛出异常， 主线程正常执行
 *
 * 使用线程池优化线程
 * @author gino
 * 2021-11-30
 */
public class OptimizeTask {
    public static volatile int i=1;

    public static void main(String[] args) throws InterruptedException {
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2);
        //0表示首次执行任务的延迟时间，40表示每次执行任务的间隔时间，TimeUnit.MILLISECONDS执行的时间间隔数值单位

        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("start ......"+i);
                if(i%2==0){
                    throw new RuntimeException();
                }
                System.out.println("end ......"+i);
                i++;
            }
        }, 0, 40, TimeUnit.MILLISECONDS);
        Thread.sleep(5000);
        scheduled.shutdown();
    }

}
