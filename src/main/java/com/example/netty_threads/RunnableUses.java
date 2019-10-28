/**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-10-28 14:06
 */

package com.example.netty_threads;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunnableUses implements Runnable {
    @Override
    public void run() {
        String host = "127.0.0.1";
        int port = 8010;
        TimeClient timeClient = new TimeClient();
        try {
            timeClient.connect(port, host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //
    // public void start() {
    //     log.info("Starting " + threadName);
    //     if (thread == null) {
    //         thread = new Thread(this, threadName);
    //         thread.start();
    //     }
    // }
}
