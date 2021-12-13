package com.ljq.mydemo.thread.Interrup;

import java.math.BigInteger;
import java.util.concurrent.BlockingDeque;

/**
 * 如何让一个线程终止
 * 中断
 *
 * @author gino
 * 2021-11-29
 */
public class BrokenPrimeProducer extends Thread {

    private final BlockingDeque<BigInteger> queue;


    public BrokenPrimeProducer(BlockingDeque<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            boolean interrupted = Thread.currentThread().isInterrupted();
            System.out.println(interrupted);
            while (!Thread.currentThread().isInterrupted()) {
                p = p.nextProbablePrime();
                System.out.println("put======================" + p);
                queue.put(p);
            }

            System.out.println("当前线程进入中断状态"+interrupted);
            System.out.println("当前线程中断状态"+Thread.currentThread().isInterrupted());

            System.out.println("当前线程中断恢复---状态");
            /**
             * 随然发生中断（调用了interrupt） 只是修改了线程的中断状态值为true
             * 但是程序人仍然会运行至结束
             * 而只有调用 interrupted方法才能 清除中断的状态
             */
            System.out.println("二次中断");
            cancel();
            System.out.println("二次中断后的中断标识: "+Thread.currentThread().isInterrupted());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 执行中断
     */
    public void cancel() {
        interrupt();
    }


    /**
     * 恢复中断
     *
     * @return
     */
    public static boolean interrupted() {
        return interrupted();
    }

    public void get() {
        System.out.println(queue.toString());
    }
}