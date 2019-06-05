package com.liumapp.booklet.thread.pool;

import com.liumapp.booklet.thread.runnable.longTimeJob;
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
    public void testCreateThreadPool () throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(4, 8,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        for (int i = 0; i < 30; i++) {
            executorService.execute(new longTimeJob());
        }
        while (!executorService.isShutdown()) {
            Thread.sleep(1000);
        }
    }

}
