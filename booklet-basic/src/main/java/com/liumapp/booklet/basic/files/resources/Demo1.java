package com.liumapp.booklet.basic.files.resources;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        OutputStream os = null;

        try {
            //read from resources
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("info.txt");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            log.info(new String(bytes));

            //write to target resources
            String resoucePath = Demo1.class.getResource("/").getPath();
            String filePath = resoucePath + "/run-data/info.txt";
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            os = new FileOutputStream(file);
            os.write(bytes);
            os.flush();
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }


    }

}
