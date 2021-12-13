package com.ljq.mydemo.thread.uncaugphtExcePtionHandler;

import java.util.concurrent.*;

/**
 * @author gino
 * 2021-12-07
 */
public class submit2 {

    public static void main(String[] args) {


        //自定一线程未捕获一异常处理器
        UEHlogger handler=new UEHlogger();

        //线程池测试
        ExecutorService executorService =new ThreadPoolExecutor(2, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        //
        Future submit = executorService.submit(new Runnable() {
            @Override
            public void run() {
                //submit的的方式提交无论时Callable 还是 Runnable 都不会使得为受检异常处理器生效
                Thread.setDefaultUncaughtExceptionHandler(handler);

                System.out.println("进入Runnable=========");
                System.out.println(1/0);
            }
        });
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
