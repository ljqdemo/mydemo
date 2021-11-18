package com.ljq.mydemo.thread.compute;

/**
 *
 * 定义计算的接口
 *包含一个 用户计算的函数式接口
 * @author gino
 * 2021-11-17
 */
public interface Computable<A,V> {

    /**
     * 定义函数式接口
     * @param ags
     * @return
     * @throws InterruptedException
     */
    public V compute(A ags) throws InterruptedException;
}
