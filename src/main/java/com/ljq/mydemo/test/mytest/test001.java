package com.ljq.mydemo.test.mytest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gino
 * 2022-01-07
 */
public class test001 {
    public static void main(String[] args) {
        ConcurrentHashMap<String,String> map=new ConcurrentHashMap<>(16);
        map.put("1","1111");
       //
        ReentrantReadWriteLock writeLock=new ReentrantReadWriteLock();
        writeLock.readLock().lock();

        writeLock.readLock().unlock();
    }
}
