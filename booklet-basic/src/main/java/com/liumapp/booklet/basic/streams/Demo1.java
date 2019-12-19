package com.liumapp.booklet.basic.streams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * demo for streams , read bytes
 * file Demo1.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/12/19
 */
public class Demo1 {

    public static void main(String[] args) throws IOException {
        //从resources目录中取流
        InputStream is = null;
        ByteArrayInputStream byteArrayInputStream = null;
        byte[] bytes1 = null;
        byte[] bytes2 = null;
        byte[] bytes3 = null;
        byte[] bytes4 = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("info.txt");
            bytes1 = new byte[is.available()];
            bytes2 = new byte[is.available()];
            bytes3 = new byte[is.available()];
            bytes4 = new byte[is.available()];
            is.read(bytes1);
            System.out.println(new String(bytes1));


            /**
             * 只有 ByteArrayInputStream可以通过reset重复读
             */
            try {
                is.reset();
                is.read(bytes2);
                System.out.println(new String(bytes2));
            } catch (IOException e) {
                System.out.println("只有 ByteArrayInputStream可以通过reset重复读");
            }


            byteArrayInputStream = new ByteArrayInputStream(bytes1);
            byteArrayInputStream.read(bytes3);
            System.out.println(new String(bytes3));

            byteArrayInputStream.reset();
            byteArrayInputStream.read(bytes4);
            System.out.println(new String(bytes4));

        }  finally {
            if (is != null) {
                is.close();
            }
        }


    }


}
