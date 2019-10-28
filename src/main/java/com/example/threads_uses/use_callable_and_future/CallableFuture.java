/**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-10-23 10:49
 */

package com.example.threads_uses.use_callable_and_future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class CallableFuture implements Callable<Integer> {

    public Integer call() throws Exception {
        int i = 0;
        for (;i < 100; i++) {
            log.info(Thread.currentThread().getName() + " " + i);
        }
        return i;
    }

    public static void main(String[] args) {
        CallableFuture callable = new CallableFuture();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        for (int i = 0; i < 100; i++) {
            log.info(Thread.currentThread().getName() + " 的循环变量i的值 " + i);
            if (i == 20) {
                new Thread(futureTask, "有返回值的线程").start();
            }
        }
        try {
            log.info("子线程的返回值：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}














