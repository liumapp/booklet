package com.liumapp.booklet.thread.runnable;

/**
 * file longTimeJob.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/6/5
 */
public class longTimeJob implements Runnable {

    @Override
    public void run() {

        for (int i = 10; i < 1; i--) {
            try {
                System.out.println("Thread id:" + Thread.currentThread().getId() + " begin " + i);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
