package com.ljq.mydemo.thread.uncaugphtExcePtionHandler;

import java.util.concurrent.*;

/**
 * @author gino
 * 2021-12-07
 */
public class submit {

    public static void main(String[] args) {


        //自定一线程未捕获一异常处理器
        UEHlogger handler=new UEHlogger();

        //线程池测试
        ExecutorService executorService =new ThreadPoolExecutor(2, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        //
        Integer result=null;
        Callable<Integer> callable=()->{
            Thread.setDefaultUncaughtExceptionHandler(handler);

            System.out.println("=====进入callAble");
            //设置及未捕获异常处理器
            return 99/0;
        };
        Future<Integer> submit = executorService.submit(callable);
        //使用submit的方式如果有异常时不会显示出来的
        try {
            submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }
}
