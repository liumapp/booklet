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
        ExecutorService executorService = new ThreadPoolExecutor(4, 8,
                60L, TimeUnit.SECONDS,
                queue);
        for (int i = 0; i < 30; i++) {
            executorService.execute(new LongTimeJob());
        }
        executorService.awaitTermination(1000, TimeUnit.MINUTES);
    }

}
