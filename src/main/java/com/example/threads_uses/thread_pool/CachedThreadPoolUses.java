/**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-10-24 11:56
 */

package com.example.threads_uses.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolUses {
    public static void main(String[] args) {
        // 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
        ExecutorService pool = Executors.newCachedThreadPool();
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

        // 关闭线程池
        pool.shutdown();
    }
}
