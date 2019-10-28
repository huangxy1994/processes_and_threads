/**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-10-24 11:43
 */

package com.example.threads_uses.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolUses {
    public static void main(String[] args) {
        // 创建一个可重用固定线程数的线程池，可控制线程最大并发数，超出的线程会在队列中等待
        ExecutorService pool = Executors.newFixedThreadPool(2);
        ExecutorService pool2 = Executors.newFixedThreadPool(5);
        // 创建线程
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();
        // 将线程放入线程池中执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool2.execute(t1);
        pool2.execute(t2);
        pool2.execute(t3);
        pool2.execute(t4);
        pool2.execute(t5);
        // 关闭线程池
        pool.shutdown();
        pool2.shutdown();
    }
}