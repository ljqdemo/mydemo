package com.ljq.mydemo.thread.readWrite;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gino
 * 2021-07-13
 *
 * 模拟解决多线程循环循序执行条件
 * ReentrantLock+Condition 实现
 */
public class ReentrantLockTest {

    private static String STR="hello more thread !";

    /**
     * 定义三个线程名
     */
    private static final String FLAG_THREAD_1 = "ReentrantLock_Thread1";
    private static final String FLAG_THREAD_2 = "ReentrantLock_Thread2";
    private static final String FLAG_THREAD_3 = "ReentrantLock_Thread3";


    public static void main(String[] args) {
        //ReentrantLock false 不公平锁  true公平锁
        ReentrantLock reentrantLock = new ReentrantLock(true);
        //构造三个条件
        Condition condition1 = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();
        Condition condition3 = reentrantLock.newCondition();

        FairRunnable fairRunnable = new FairRunnable(reentrantLock,condition1,condition2,condition3);

        new Thread(fairRunnable,FLAG_THREAD_2).start();
        new Thread(fairRunnable,FLAG_THREAD_3).start();
        new Thread(fairRunnable,FLAG_THREAD_1).start();


    }
    static class FairRunnable implements Runnable{
        private ReentrantLock lock;
        private Condition condition1;
        private Condition condition2;
        private Condition condition3;

        public FairRunnable(ReentrantLock lock, Condition condition1, Condition condition2, Condition condition3) {
            this.lock = lock;
            this.condition1 = condition1;
            this.condition2 = condition2;
            this.condition3 = condition3;
        }

        @Override
        public void run() {
            while (true){
                //当前线程获取锁
                try{
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " start");
                    Thread.sleep(1000);
                    switch (Thread.currentThread().getName()) {
                        case FLAG_THREAD_1:
                            //唤醒线程2 自身线程挂起阻塞
                            condition2.signal();
                            condition1.await();
                            break;
                        case FLAG_THREAD_2:
                            //唤醒线程3 自身线程挂起阻塞
                            condition3.signal();
                            condition2.await();
                            break;
                        case FLAG_THREAD_3:
                            //唤醒线程1 自身线程挂起阻塞
                            condition1.signal();
                            condition3.await();
                            break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    lock.unlock();
                }



            }
        }
    }
}
