/**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-10-24 11:59
 */

package com.example.threads_uses.thread_pool;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolUses {
    public static void main(String[] args) {
        // 创建一个定长线程池，支持定时及周期性任务执行
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(new Runnable() {  // 每隔一段时间触发异常
            public void run() {
                System.out.println("==============");
            }
        }, 1000, 5000, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println(System.nanoTime());
            }
        }, 1000, 2000, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("hello world!");
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }
}
