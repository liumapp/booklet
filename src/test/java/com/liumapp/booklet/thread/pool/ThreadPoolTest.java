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

    /**
     * 永远不要使用Executors来创建ThreadPoolExecutor
     * 也不要使用LinkedBlockingQueue来放任务
     */
    @Test
    public void testCreateThreadPool () throws InterruptedException {
        //错误但能运行的演示
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(); //会使用Integer.MAX_VALUE作为默认size值，成为一个无边界的阻塞队列，这种情况极容易因为任务过多而造成内存溢出
        //正确但不能运行的演示
//        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(5); //限制允许放置的最大任务数目，当超出最大值的情况下，如何处理请看test2

        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8,
                60L, TimeUnit.SECONDS,
                queue);
        for (int i = 0; i < 30; i++) {
            //放置30条任务
            //如果使用最大size为5的ArrayBlockingQueue来放置任务，将会报RejectedExecutionException异常
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

//    public void testHanding



}
