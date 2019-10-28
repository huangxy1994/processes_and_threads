package com.example.netty_threads; /**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-10-24 16:26
 */

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
    // 核心线程数
    private static final int CORE_POOL_SIZE = 3;
    // 线程池所能容纳的最大线程数
    private static final int MAXIMUM_POOL_SIZE = 5;
    // 线程闲置超时时长
    private static final long KEEP_ALIVE = 60;

    private ExecutorService threadPool;

    private volatile static ThreadPool INSTANCE = null;

    private ThreadPool() {
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue(8),
                new DefaultThreadFactory());
    }

    public synchronized static ThreadPool getInstance() {
        if (INSTANCE == null) {
            synchronized(ThreadPool.class) {
                INSTANCE = new ThreadPool();
            }
        }
        return INSTANCE;
    }

    public void addTask(Runnable runnable) {
        threadPool.execute(runnable);
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    public static void main(String[] args) {
        ThreadPool pool = ThreadPool.getInstance();

        // 创建线程
        RunnableUses run = new RunnableUses();
        RunnableUses run2 = new RunnableUses();
        RunnableUses run3 = new RunnableUses();

        // 将线程放入线程池中执行
        pool.addTask(run);
        System.out.println("=========================================================");
        pool.addTask(run2);
        System.out.println("=========================================================");
        pool.addTask(run3);

        // 关闭线程池
        pool.shutdown();
    }
}

class DefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    // private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    DefaultThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        namePrefix = "pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(new ThreadGroup("group" + (poolNumber.getAndIncrement() - 1)), r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}