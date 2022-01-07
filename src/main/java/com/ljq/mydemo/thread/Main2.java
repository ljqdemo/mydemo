package com.ljq.mydemo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author gino
 * 2021-07-13
 */

public class Main2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread j=new MyThread();
        MyThread j2=new MyThread();
        MyThread j3=new MyThread();
        MyThread j4=new MyThread();

        int i = System.identityHashCode(j);
        System.out.println("i:"+i);
        int i2 = System.identityHashCode(j2);
        int i3 = System.identityHashCode(j3);
        int i4 = System.identityHashCode(j4);
        System.out.println("i2:"+i2);
        System.out.println("i3:"+i3);
        System.out.println("i4:"+i4);
    }

    public static void test() throws ExecutionException, InterruptedException {
        // 创建一个适配器
        FutureTask futureTask = new FutureTask(new MyThread());
        new Thread(futureTask, "A").start();
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
