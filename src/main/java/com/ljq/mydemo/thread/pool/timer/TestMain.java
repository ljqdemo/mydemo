package com.ljq.mydemo.thread.pool.timer;

import java.util.Timer;

/**
 *
 * 用Timer定时任务则会出现以下问题
 *
 * 1.当其中一个Task异常的时候整个Timer 会被认定为取消状态 从而导致整个的Timer都会被取消
 * 2.当周期性的执行某两个任务的时候， 如果某个任务的执行时间时间大于所其他的执行任务的执行周期， 那个这个执行任务的执行会影响其他的执行任务的定时的精准行
 *
 * 应该使用线程池的方式创建
 *  optimize
 * @author gino
 * 2021-11-30
 */
public class TestMain {

    private static Timer timer=new Timer();

    public static void main(String[] args) throws InterruptedException {

        TestTask  task1=new TestTask(true);
        System.out.println("start task1===========");
        timer.schedule(task1,1);

        
        Thread.sleep(5000);
        TestTask  task2=new TestTask(false);
        timer.schedule(task2,1);
        System.out.println("end task2===========");
    }
}
