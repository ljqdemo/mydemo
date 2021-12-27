package com.ljq.mydemo.thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * 定制线程池处理接口
 *前至拦截后置拦截
 * @author gino
 * 2021-12-10
 */
public class TimingThreadPool extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final Logger log = Logger.getLogger("TimingThreadPool");

    private final AtomicLong munTask = new AtomicLong();

    private final AtomicLong totalTime = new AtomicLong();


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.fine(String.format("Thread :%s  Start: %s",t,r));
        startTime.set(System.nanoTime());
        System.out.println(String.format("Thread :%s  Start: %s",t,r));
    }

    @Override
    protected void terminated() {
        super.terminated();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
       try{
           Long endTime = System.nanoTime();
           Long  taskTime=endTime-startTime.get();
           munTask.incrementAndGet();
           totalTime.addAndGet(taskTime);
           log.fine(String.format("Thread :%s  End: %s,time=%dns",t,r,taskTime));
           System.out.println(String.format("Thread :%s  End: %s,time=%dns",t,r,taskTime));
       }finally {
           super.afterExecute(r, t);
       }
    }



    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public static void main(String[] args) {

        TimingThreadPool timingThreadPool = new TimingThreadPool(2, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        for (int i = 0; i <2 ; i++) {
            int finalI = i;
            timingThreadPool.execute(()->{
                if (finalI >0){
                    System.out.println("222222222222222222222");
                }else {
                    System.out.println("111111111111111111111111");
                }

            });
        }
        timingThreadPool.shutdown();
    }
}
