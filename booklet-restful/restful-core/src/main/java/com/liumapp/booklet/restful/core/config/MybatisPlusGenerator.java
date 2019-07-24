package com.liumapp.booklet.restful.core.config;

/**
 * 生成mybatis-plus模板文件
 * 定义在config包下，因为每次数据库建表后，需要运行该文件来生成对应的domain与mapper文件，类似于配置的概念
 * file MybatisPlusGenerator.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/24
 */
public class MybatisPlusGenerator {

    private static String tableNames = "product_a," +
            "product_b," +
            "users";

    public static void main(String[] args) {

    }

}
