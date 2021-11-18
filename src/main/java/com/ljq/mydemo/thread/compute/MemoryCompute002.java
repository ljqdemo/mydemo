package com.ljq.mydemo.thread.compute;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 中级demo
 * 解决初级demo  的问题，
 * 改成每次使用Future来判断是否有线程正在计算
 * 如果有线程正在执行计算于当前线程相同的计算  则直接阻塞等待 上个线程的结果直接，等上一个线程将结果计算完毕  直接从cache中拿
 *
 *
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
public class MemoryCompute002<A, V> implements Computable<A, V> {
    /**
     * 为了保证并发线程安全，以及线程安全的效率
     * 所用采用ConcurrentHashMap  而不是采用synchronized
     * 因为ConcurrentHashMap 采用的是分段锁， 而synchronized 会锁住整个方法
     * <p>
     * 缓存计算参数以及计算接口
     */
    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap();

    /**
     * 定义计算的计算器，计算器通过构造函数传递
     */
    private final Computable<A, V> computable;


    public MemoryCompute002(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A ags) throws InterruptedException {
        V result = null;
        Random random=new Random();
        int i = random.nextInt(5);
        Thread.sleep(i*1000);
        System.out.println(Thread.currentThread().getId()+" starting ");
        //依旧是先取缓存  ，不过取到的不是V  而是Future
        Future<V> f = cache.get(ags);
        if (Objects.isNull(f)) {
            //判断是否有任务正在执行于当前一样的计算，没有则执行计算
            Callable exc = new Callable() {
                @Override
                public Object call() throws Exception {
                    return computable.compute(ags);
                }
            };
            FutureTask<V> ft = new FutureTask<>(exc);
            f = ft;
            //将计算任务加入缓存(如果不存在才加入缓存)
            cache.putIfAbsent(ags, ft);
            //开始计算
            System.out.println(Thread.currentThread().getName()+": get cache fail ,is computing.......");
            ft.run();

        }
        try {
            result = f.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }
}
