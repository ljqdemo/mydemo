package com.ljq.mydemo.thread.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @author gino
 * 2022-01-04
 */
public class BoundBuffer<E> {
    /**
     * 已有元素
     */
    private Semaphore availableItems;

    /**
     * 可用空间
     */
    private Semaphore availableSpace;

    /**
     * put位置
     */
    private int putPosition=0;

    /**
     * take位置
     */
    private int takePosition=0;

    /**
     * 集合元素
     */
    private final E[] items;


    public BoundBuffer(int capacity){
        availableItems=new Semaphore(0);
        availableSpace=new Semaphore(capacity);
        items= (E []) new Object[capacity];
    }

    public boolean isEmpty(){
        return availableItems.availablePermits()==0;
    }

    public boolean isFull(){
        return availableSpace.availablePermits()==0;
    }

    public void put(E item) throws InterruptedException {
        //申请，可用空间缓存(将可用的空间减1)
        availableSpace.acquire();
        doInsert(item);
        //申请，释放一个现有的资源的信号量（将已经使用的空间加1）
        availableItems.release();
    }

    public E  take() throws InterruptedException {
        //申请，释放一个现有的资源的信号量
        availableItems.acquire(5);
        E item=doExtra();
        //申请，可用空间缓存
        availableSpace.release();
        return  item;
    }


    private synchronized E doExtra() {
        int i=takePosition;
        E item=items[i];
        items[i]=null;
        takePosition=(++i== items.length)?0:i;
        return item;
    }

    private synchronized void doInsert(E item) {
        int i=putPosition;
        items[i]=item;
        putPosition=(++i== items.length)?0:i;
    }
}
