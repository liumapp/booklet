package com.liumapp.booklet.restful.portal;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liumapp.booklet.restful.core.db.entity.ProductA;
import com.liumapp.booklet.restful.core.db.entity.ProductB;
import com.liumapp.booklet.restful.core.db.entity.Users;
import com.liumapp.booklet.restful.core.db.mapper.ProductAMapper;
import com.liumapp.booklet.restful.core.db.mapper.ProductBMapper;
import com.liumapp.booklet.restful.core.db.mapper.UsersMapper;
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

    @Autowired
    private UsersMapper mapperUsers;

    @Test
    public void testSelect () {
        System.out.println(("----- selectAll method test ------"));
        List<ProductA> aList = mappera.selectList(null);
        List<ProductB> bList = mapperb.selectList(null);
        List<Users> userList = mapperUsers.selectList(null);

        Assert.assertEquals(4, aList.size());
        aList.forEach(System.out::println);

        Assert.assertEquals(4, bList.size());
        bList.forEach(System.out::println);

        Assert.assertEquals(1, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testPage1 () {
        Page<ProductA> page = new Page<>(1, 3);
        QueryWrapper<ProductA> wrapper = new QueryWrapper<>();
//        wrapper.like("name", "Audi");
        IPage<ProductA> result = mappera.selectPage(page, wrapper);
        System.out.println(result.getTotal());
        Assert.assertTrue(result.getTotal() > 3);
        Assert.assertEquals(3, result.getRecords().size());
    }

    @Test
    public void testPage2 () {
        System.out.println("----- baseMapper 自带分页 ------");
        Page<ProductA> page = new Page<>(2, 2);
        IPage<ProductA> userIPage = mappera.selectPage(page, new QueryWrapper<ProductA>());

        System.out.println("总条数 ------> " + userIPage.getTotal());
        System.out.println("当前页数 ------> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userIPage.getSize());
        System.out.println(userIPage.getRecords());
        System.out.println("----- baseMapper 自带分页 ------");

    }


}