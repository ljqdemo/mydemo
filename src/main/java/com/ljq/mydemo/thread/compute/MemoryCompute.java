package com.ljq.mydemo.thread.compute;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初级demo     但是这样也会同时也会存在一定的局限性
 * （就是当一个线程正在计算的时候，另外一个线程发现缓存中没有结果， 此时也会去计算，这样其实就会出现计算两次相同的结果
 * 如果计算的时间很长，就会浪费一部分时间  ，而我们理想的状态应该是只计算一次就行了，如果发现有线程在计算结果了，当前的线程不应该继续计算
 * 而是，等待前一个线程的计算结果[这才是最明智的选择]
 *
 *在这个问题将会在下一个版本（博客） 介绍
 * ）
 *
 *
 * <p>
 * 实现一个带有 “记忆”  功能计算接口
 * 该接口是使用于  多线程 ，计算费时长  的任务
 * <p>
 * A  参与计算的参数
 * V  参与计算的结果
 *
 * @author gino
 * 2021-11-17
 */
public class MemoryCompute<A, V> implements Computable<A, V> {
    /**
     * 为了保证并发线程安全，以及线程安全的效率
     * 所用采用ConcurrentHashMap  而不是采用synchronized
     * 因为ConcurrentHashMap 采用的是分段锁， 而synchronized 会锁住整个方法
     * <p>
     * 缓存计算参数以及计算接口
     */
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();

    /**
     * 定义计算的计算器，计算器通过构造函数传递
     */
    private final Computable<A, V> computable;


    public MemoryCompute(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A ags) throws InterruptedException {
        //先读取缓存
        V result = cache.get(ags);
        System.out.println(Thread.currentThread().getId()+": get cache value : "+result  + " key is "+ags);
        if (Objects.isNull(result)) {
            System.out.println(Thread.currentThread().getName()+": get cache fail ,is computing.......");
            //缓存为空则需要重新计算
            result = computable.compute(ags);
            //将计算结果加入缓存
            cache.put(ags, result);
        }
        return result;
    }
}
