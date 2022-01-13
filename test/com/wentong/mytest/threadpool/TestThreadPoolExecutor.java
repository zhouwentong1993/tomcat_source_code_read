package com.wentong.mytest.threadpool;

import org.apache.tomcat.util.threads.TaskQueue;
import org.apache.tomcat.util.threads.TaskThreadFactory;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor {

    @Test
    public void test() throws Exception {
        TaskQueue queue = new TaskQueue(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                4,
                8,
                10, TimeUnit.SECONDS,
                queue,
                new TaskThreadFactory("test", false, 5));
        queue.setParent(executor);
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println("Current Thread: " + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(10000);

    }

}
