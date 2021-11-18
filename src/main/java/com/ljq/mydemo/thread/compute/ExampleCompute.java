package com.ljq.mydemo.thread.compute;


import java.math.BigInteger;

/**
 *
 *自定义具体的计算器
 * 里面是具体的实现逻辑
 * @author gino
 * 2021-11-17
 */
public class ExampleCompute implements Computable<String, BigInteger> {


    @Override
    public BigInteger compute(String ags) throws InterruptedException {
        //自定的一些关于怎么去计算的方法
        //这是具体怎么去计算的代码
        Thread.sleep(1000);
        return new BigInteger(ags) ;
    }
}
