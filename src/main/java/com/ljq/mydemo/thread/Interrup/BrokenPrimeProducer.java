package com.ljq.mydemo.thread.Interrup;

import java.math.BigInteger;
import java.util.concurrent.BlockingDeque;

/**
 *中断
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
            if (interrupted) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}