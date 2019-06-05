package com.liumapp.booklet.thread.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * file LongTimeJob.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/6/5
 */
public class LongTimeJob implements Runnable {

    @Override
    public void run() {
        for (int i = 10; i > 1; i--) {
            try {
                System.out.println("Thread id:" + Thread.currentThread().getId() + " begin " + i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
