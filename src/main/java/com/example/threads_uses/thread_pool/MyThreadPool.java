/**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-10-24 14:59
 */

package com.example.threads_uses.thread_pool;

import com.example.threads_uses.use_runnable.UseRunnable;

import java.util.concurrent.*;

public class MyThreadPool {
    // 核心线程数
    private static final int CORE_POOL_SIZE = 3;
    // 线程池所能容纳的最大线程数
    private static final int MAXIMUM_POOL_SIZE = 5;
    // 线程闲置超时时长
    private static final long KEEP_ALIVE = 60;

    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue(8));

        // 创建线程
        UseRunnable runnable1 = new UseRunnable("Thread-1");
        UseRunnable runnable2 = new UseRunnable("Thread-2");
        UseRunnable runnable3 = new UseRunnable("Thread-3");

        // 将线程放入线程池中执行
        threadPool.execute(runnable1);
        threadPool.execute(runnable2);
        threadPool.execute(runnable3);

        // 关闭线程池
        threadPool.shutdown();
    }
}
