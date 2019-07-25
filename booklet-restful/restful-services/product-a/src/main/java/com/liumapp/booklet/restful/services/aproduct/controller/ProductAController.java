package com.liumapp.booklet.restful.services.aproduct.controller;

import com.liumapp.booklet.restful.core.annotations.Log;
import com.liumapp.booklet.restful.core.beans.ResultBean;
import com.liumapp.booklet.restful.core.consts.LogConst;
import com.liumapp.booklet.restful.core.db.entity.ProductA;
import com.liumapp.booklet.restful.services.aproduct.consts.ProductALogConst;
import com.liumapp.booklet.restful.services.aproduct.services.ProductAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/all")
    @Log(action = LogConst.ACTION_QUERY, itemType = ProductALogConst.ITEM_TYPE_PRODUCT_A)
    public ResultBean<Collection<ProductA>> getAll () {
        return new ResultBean<Collection<ProductA>>(service.getAll());
    }

    
    public ResultBean<Long> add (ProductA productA) {
        return null;
    }

}
