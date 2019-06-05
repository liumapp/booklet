package com.liumapp.booklet.thread.pool;

import com.liumapp.booklet.thread.runnable.LongTimeJob;
import org.junit.Test;

import java.sql.Time;
import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * file ThreadPoolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/6/5
 */
public class ThreadPoolTest {

    @Test
    public void testCreateThreadPool () throws InterruptedException {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8,
                60L, TimeUnit.SECONDS,
                queue);
        for (int i = 0; i < 30; i++) {
            executor.execute(new LongTimeJob());
        }
        while(!executor.isTerminated()) {
            System.out.println("task number: " + executor.getTaskCount());
            System.out.println("active count: " + executor.getActiveCount());
            System.out.println("completed task count: " + executor.getCompletedTaskCount());
            Thread.sleep(1000);
            if (executor.getCompletedTaskCount() == executor.getTaskCount()) {
                executor.shutdown();
            }
        }
    }



}
