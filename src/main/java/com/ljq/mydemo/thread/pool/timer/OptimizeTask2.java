package com.ljq.mydemo.thread.pool.timer;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 *固定延时执行
 * 使用线程池不会造成当执行线程失败的时候，导致主线程也异常退出
 *
 * 使用线程池优化线程
 * @author gino
 * 2021-11-30
 */
public class OptimizeTask2 {
    public static volatile int i=1;

    public static void main(String[] args) throws InterruptedException {
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2);
        //0表示首次执行任务的延迟时间，40表示每次执行任务的间隔时间，TimeUnit.MILLISECONDS执行的时间间隔数值单位
        scheduled.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("start ......"+i);
                if(i>3){
                    throw new RuntimeException();
                }
                System.out.println("end ......"+i);
                i++;
            }
        }, 0, 50, TimeUnit.MILLISECONDS);



        Thread.sleep(6000);

        System.out.println(scheduled.isShutdown());

        scheduled.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("start2 ......"+i);
                if(i>6){
                    throw new RuntimeException();
                }
                System.out.println("end2 ......"+i);
                i++;
            }
        }, 0, 50, TimeUnit.MILLISECONDS);

        //要让主线程等待，否则主线程退出就无法看第二个线程的结果了
        Thread.sleep(5000);

        scheduled.shutdown();
    }

}
