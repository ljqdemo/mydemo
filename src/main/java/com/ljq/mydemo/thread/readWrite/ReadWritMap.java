package com.ljq.mydemo.thread.readWrite;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 实现一个线程安全的map 通过ReentrantReadWriteLock
 * 读写隔离， 写写隔离，读读不隔离
 *
 * @author gino
 * 2022-01-10
 */
public class ReadWritMap<K, V> {
    private final Map<K, V> map;
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock r = readWriteLock.readLock();
    private final Lock w = readWriteLock.writeLock();

    public ReadWritMap(Map<K, V> map) {
        this.map = map;
    }


    /**
     * 写要隔离加入写锁
     *
     * @param k
     * @param v
     */
    public V put(K k, V v) {
        w.lock();
        try {
            return map.put(k, v);
        } finally {
            w.unlock();
        }
    }

    /**
     * 读
     * @param k
     * @return
     */
    public V get(K k) {
        r.lock();
        try {
            return map.get(k);
        } finally {
            r.unlock();
        }
    }
}
