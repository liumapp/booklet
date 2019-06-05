package com.liumapp.booklet.thread.pool;

import org.junit.Test;

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
    public void testCreateThreadPool () {
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

}
