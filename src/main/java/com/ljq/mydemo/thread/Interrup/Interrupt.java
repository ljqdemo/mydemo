package com.ljq.mydemo.thread.Interrup;

/**
 * @author gino
 * 2021-11-30
 */
public class Interrupt {
    public static void main(String[] args) {
        Thread t = new Thread(new Worker());
        t.start();

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
        System.out.println("Main thread stopped.");
    }

    public static class Worker implements Runnable {

        @Override
        public void run() {
            System.out.println("Worker started.");
            boolean f; // 用于检测interrupted()第一次返回值
            int i = 0;
            Thread c = Thread.currentThread();
            System.out.println("while之前线程中断状态isInterrupted()：" + c.isInterrupted());
            while (!(f = Thread.interrupted())) {// 判断是否中断，如果中断，那么跳出并清除中断标志位
                // 一旦检测到中断，interrupted()第一次返回true，就可以跳出循环，第二次以及以后都是返回false
                System.out.println("while内，还没中断，interrupted()返回值为：" + f);
                System.out.println(c.getName() + "  " + i++ + "  " + c.isInterrupted());
            }
            System.out.println("跳出循环即第一次中断interrupted()返回值：" + f);
            System.out.println("while之后线程中断状态isInterrupted():" + c.isInterrupted()); // 为false，因为interrupt()会清除中断标志位，显示为未中断
            System.out.println("第二次及以后的interrupted()返回值：" + Thread.interrupted());
            c.interrupt();
            System.out.println("再次中断后查询中断状态isInterrupted():" + c.isInterrupted());
            System.out.println("Worker stopped.");
        }
    }
}
