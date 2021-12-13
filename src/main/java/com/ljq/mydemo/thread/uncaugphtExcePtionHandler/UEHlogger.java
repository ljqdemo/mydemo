package com.ljq.mydemo.thread.uncaugphtExcePtionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author gino
 * 2021-12-07
 */
public class UEHlogger implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE,"捕获未受检的异常，线程名称:"+t.getName());
        logger.log(Level.SEVERE,"线程发生中断，线程名称:"+t.getName(),e);
    }
}
