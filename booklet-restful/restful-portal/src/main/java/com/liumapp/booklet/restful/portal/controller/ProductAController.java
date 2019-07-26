package com.liumapp.booklet.restful.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liumapp.booklet.restful.core.annotations.Log;
import com.liumapp.booklet.restful.core.beans.ResultBean;
import com.liumapp.booklet.restful.core.db.entity.ProductA;
import com.liumapp.booklet.restful.portal.consts.ProductALogConst;
import com.liumapp.booklet.restful.portal.services.ProductAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * file ProductAController.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/23
 */
@RestController
@RequestMapping("/proa")
public class ProductAController {

    @Autowired
    private ProductAService service;

    @RequestMapping("/all")
    @Log(action = ProductALogConst.ACTION_QUERY, itemType = ProductALogConst.ITEM_TYPE_PRODUCT_A)
    public ResultBean<Collection<ProductA>> getAll () {
        return new ResultBean<Collection<ProductA>>(service.getAll());
    }

    @RequestMapping("/page")
    @Log(action = ProductALogConst.ACTION_QUERY, itemType = ProductALogConst.ITEM_TYPE_PRODUCT_A)
    public ResultBean<IPage<ProductA>> getPage () {
        Page<ProductA> page = new Page<>();
        return new ResultBean<IPage<ProductA>>(service.selectUserPage(page));
    }

    @RequestMapping("/add")
    @Log(action = ProductALogConst.ACTION_ADD, itemType = ProductALogConst.ITEM_TYPE_PRODUCT_A, itemId = "#productA.name")
    public ResultBean<Long> add (@RequestBody ProductA productA) {
        return new ResultBean<Long>(service.add(productA));
    }

}
