/**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-10-24 11:51
 */

package com.example.threads_uses.thread_pool;

public class MyThread extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行。。。");
    }
}
