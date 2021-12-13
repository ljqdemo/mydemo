package com.ljq.mydemo.thread.uncaugphtExcePtionHandler;

import java.util.concurrent.*;

/**
 * @author gino
 * 2021-12-07
 */
public class TestMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        UEHlogger handler=new UEHlogger();

        User user=null;
        Thread thread=new Thread(new Runnable() {

            Throwable throwable=null;

            @Override
            public void run() {
                try{
                    //认为制作除0异常
                    int result=8/0;
                }catch ( Throwable e){
                    //退出线程
                    System.out.println("捕获到受检查异常......"+e.getMessage());
                }
                //再次抛出未检查的异常,测试异常处理器
                   // user.setName("空指针异常");

                //测试能否继续执行
                System.out.println("我还能执行=========");
            }
        });

        //指定线程抛出异常以后应该如果处理
        thread.setUncaughtExceptionHandler(handler);

        //启动线程执行
        thread.start();

        //线程池测试
        ExecutorService executorService =new ThreadPoolExecutor(2, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());


        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Thread.setDefaultUncaughtExceptionHandler(handler);
                try{
                    //认为制作除0异常
                    int result=8/0;
                }catch ( Throwable e){
                    //退出线程
                    System.out.println("捕获到受检查异常......"+e.getMessage());
                }
                //再次抛出未检查的异常,测试异常处理器
                user.setName("空指针异常");

                //测试能否继续执行
                System.out.println("我还能执行=========");
            }
        });


        //
        Integer result=null;
        Callable<Integer> callable=()->{
            System.out.println("=====进入callAble");
            return 99/0;
        };
        Future<Integer> submit = executorService.submit(callable);
        //使用submit的方式如果有异常时不会显示出来的
        submit.get();
        executorService.shutdown();
    }


    class User{
        private String name;

        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
