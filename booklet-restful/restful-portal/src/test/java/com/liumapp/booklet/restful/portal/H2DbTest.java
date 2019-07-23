package com.liumapp.booklet.restful.portal;

import com.liumapp.booklet.restful.core.domain.ProductA;
import com.liumapp.booklet.restful.core.domain.ProductB;
import com.liumapp.booklet.restful.core.mapper.ProductAMapper;
import com.liumapp.booklet.restful.core.mapper.ProductBMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * file H2DbTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class H2DbTest {

    @Autowired
    private ProductAMapper mappera;

    @Autowired
    private ProductBMapper mapperb;

    @Test
    public void testSelect () {
        System.out.println(("----- selectAll method test ------"));
        List<ProductA> aList = mappera.selectList(null);
        List<ProductB> bList = mapperb.selectList(null);
        Assert.assertEquals(4, aList.size());
        aList.forEach(System.out::println);
        Assert.assertEquals(4, bList.size());
        bList.forEach(System.out::println);
    }

}