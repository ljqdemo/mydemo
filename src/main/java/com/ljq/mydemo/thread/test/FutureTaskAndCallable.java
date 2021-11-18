package com.ljq.mydemo.thread.test;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author gino
 * 2021-07-13
 * FutureTask 使用FutureTask 并使得线程实现Callable接口，
 *   可以实现异步的去调用线程执行方法 并且在后面拿到方法返回值s
 *  试用 
 */
public class FutureTaskAndCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //适配器
        FutureTask funcTrue = new FutureTask(new MyThread2());

        Thread thread = new Thread(funcTrue, "A");
        thread.start();

        for (int i = 0; i <100 ; i++) {
            System.out.println(i);
        }

        System.out.println( funcTrue.get());
    }
    static class MyThread2 implements Runnable, Callable<Boolean> {
        @Override
        public void run() {
            System.out.println("I is run ");
        }

        @Override
        public Boolean call() throws Exception {
            System.out.println("I is doing call");
            return false;
        }
    }
}
