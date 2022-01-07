package com.ljq.mydemo.thread.semaphore;

/**
 * @author gino
 * 2022-01-04
 */
public class BoundBufferTest {
    public static void main(String[] args) throws InterruptedException {
        BoundBuffer<String> buffer=new BoundBuffer<>(1);
        System.out.println("isEmpty:"+buffer.isEmpty());
        System.out.println("isFull:"+buffer.isFull());

        buffer.put("1111111");

        System.out.println("isEmpty:"+buffer.isEmpty());
        System.out.println("isFull:"+buffer.isFull());

        System.out.println("take:"+buffer.take());
        System.out.println("take:"+buffer.take());



    }
}
