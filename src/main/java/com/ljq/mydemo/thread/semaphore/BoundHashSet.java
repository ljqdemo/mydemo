package com.ljq.mydemo.thread.semaphore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 使用Semaphore 为容器设置 边界值
 *
 *
 * @author gino
 * 2021-11-16
 */
public class BoundHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundHashSet(Integer bound) {
        set = Collections.synchronizedSet(new HashSet<T>());
        semaphore = new Semaphore(bound);
    }

    /**
     * 增加数据
     *
     * @param o
     * @return
     * @throws InterruptedException
     */
    public boolean add(T o) throws InterruptedException {
        //申请信号量
        semaphore.acquire();
        boolean result = false;
        //记录结果
        try {
            result = set.add(o);
            return result;
        } finally {
            if (!result) {
                //释放信号量
                semaphore.release();
            }
        }

    }

    /**
     * 获取Set
     * 不要直接返回Set 这是不安全的发布
     *
     * @return
     */
    public Set<T> getSet() {
        return Collections.synchronizedSet(set);
    }

    /**
     * 移除元素
     *
     * @param obj
     * @return
     */
    public boolean remove(Object obj) {
        boolean remove = false;
        remove = set.remove(obj);
        //移除成功信号量减一
        if (remove) {
            //释放信号量
            semaphore.release();
        }
        return remove;
    }


    public static void main(String[] args) throws InterruptedException {
        BoundHashSet hashSet = new BoundHashSet<Integer>(5);
        Thread thread1 = new Thread(() -> {
            ThreadLocalRandom.current().nextBoolean();
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println("add the thread1 of " + i + " run:" + hashSet.add(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (int i = 9; i < 14; i++) {
                try {
                    System.out.println("add the thread2 of " + i + " run:" + hashSet.add(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 9; i++) {
                System.out.println("remove the thread3 of " + i + " run:" + hashSet.remove(i));
            }
        });
        thread3.start();

        System.out.println("output result==========================");
        Set<Integer> set = hashSet.getSet();
        set.forEach(e->{
            System.out.println(e);
        });
    }
}
