package com.wentong.mytest.threadpool;

import org.apache.tomcat.util.threads.TaskQueue;
import org.apache.tomcat.util.threads.TaskThreadFactory;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
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

    /**
     * 测试当 CPU 密集型时，Java 自带的和 Tomcat 的线程池有何不同。
     */
    @Test
    public void testWhenIo() throws Exception {
        int threadCount = 50;

        CountDownLatch latch = new CountDownLatch(threadCount);
        CountDownLatch finish = new CountDownLatch(threadCount);
        long start = System.currentTimeMillis();
        java.util.concurrent.ThreadPoolExecutor executor = new java.util.concurrent.ThreadPoolExecutor(8, 100, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                latch.countDown();
                long ss = System.currentTimeMillis();
                long sum = 0;
                for (int j = 0; j < 100; j++) {
                    for (int k = 0; k < 1000000; k++) {
                        int hash = Objects.hashCode(String.valueOf(j + k));
//                        hash = Objects.hash(hash);
                        sum += hash;
                    }
                }
                System.out.println("执行时间：" + (System.currentTimeMillis() - ss));
                System.out.println(sum);
                System.out.println("-------------------------");
                finish.countDown();
            });
        }
        latch.await();
        finish.await();
        System.out.println("total time is:" + (System.currentTimeMillis() - start));
    }

}
