package com.ljq.mydemo.thread.Interrup;

import java.math.BigInteger;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author gino
 * 2021-11-30
 */
public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        BlockingDeque<BigInteger> queue=new LinkedBlockingDeque();
        BrokenPrimeProducer primeProducer=new BrokenPrimeProducer(queue);
        primeProducer.start();
        Thread.sleep(1000);
        //中断线程
        primeProducer.cancel();
        System.out.println("end....................");
        Thread.sleep(2000);
    }
}
