package com.ljq.mydemo.thread;

import java.util.concurrent.*;

/**
 * @author gino
 * 2021-07-13
 */

 public class Main2 {

        public static void main(String[] args) throws ExecutionException, InterruptedException {
            // 创建一个适配器
            FutureTask futureTask = new FutureTask(new MyThread());
            new Thread(futureTask,"A").start();
            Boolean o = (Boolean) futureTask.get();
            System.out.println(o);
        }
    }

    class MyThread implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
            return true;
        }

}
