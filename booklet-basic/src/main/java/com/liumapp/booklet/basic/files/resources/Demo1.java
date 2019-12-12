package com.liumapp.booklet.basic.files.resources;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * file Demo1.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/12/12
 */
@Slf4j
public class Demo1 {

    /**
     * read and write file from resources
     * @param args
     */
    public static void main(String[] args) throws IOException {
        InputStream is = null;

        try {
            //read from resources
             is = Thread.currentThread().getContextClassLoader().getResourceAsStream("info.txt");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            log.info(new String(bytes));

            //write to resources

        } finally {
            if (is != null) {
                is.close();
            }
        }


    }

}
