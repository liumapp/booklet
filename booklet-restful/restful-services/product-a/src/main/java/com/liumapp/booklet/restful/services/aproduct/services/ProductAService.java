package com.liumapp.booklet.restful.services.aproduct.services;

import com.liumapp.booklet.restful.core.db.entity.ProductA;
import com.liumapp.booklet.restful.core.db.mapper.ProductAMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 在service执行参数检查、日志打印等操作
 * file ProductAService.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/24
 */
@Service
public class ProductAService {

    private static final Logger logger = LoggerFactory.getLogger(ProductAService.class);

    @Autowired
    private ProductAMapper mapper;

    public Collection<ProductA> getAll () {
        //校验参数

        List<ProductA> data = mapper.selectList(null);
        return data;
    }


}
