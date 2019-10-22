/**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-10-22 17:01
 */

package com.example.threads_uses.use_runnable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UseRunnable implements Runnable {

    private Thread thread;
    private String threadName;

    public UseRunnable(String name) {
        threadName = name;
        log.info("Creating " + threadName);
    }

    public void run() {
        log.info("Running " + threadName);
        try {
            for (int i = 4; i > 0; i--) {
                log.info("Thread：" + threadName + " ===> " + i);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            log.info("Thread " + threadName + " interrupted.");
        }
        log.info("Thread " + threadName + " exiting.");
    }

    public void start() {
        log.info("Starting " + threadName);
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public static void main(String[] args) {
        UseRunnable runnable = new UseRunnable("Thread-1");
        runnable.start();
        UseRunnable runnable2 = new UseRunnable("Thread-2");
        runnable2.start();
    }
}














