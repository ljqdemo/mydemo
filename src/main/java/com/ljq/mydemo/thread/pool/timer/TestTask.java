package com.ljq.mydemo.thread.pool.timer;

import java.util.TimerTask;

/**
 *
 * 定义Timer的任务器
 * @author gino
 * 2021-11-30
 */
public class TestTask  extends TimerTask {

    private Boolean error;

    public TestTask(boolean error){
        this.error=error;
    }
    @Override
    public void run() {
        System.out.println("runing task");
        if (error) {
            throw new RuntimeException();
        }
    }
}
