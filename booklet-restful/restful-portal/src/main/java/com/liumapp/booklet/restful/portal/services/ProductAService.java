package com.liumapp.booklet.restful.portal.services;

import com.liumapp.booklet.restful.core.db.entity.ProductA;
import com.liumapp.booklet.restful.core.db.mapper.ProductAMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static com.liumapp.booklet.restful.core.util.CheckUtil.*;

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


//    public Collection<ProductA> getPage () {
//        ProductAPage<ProductA> productAPage = new ProductAPage<>(1, 3);
//        productAPage
//    }

    public Collection<ProductA> getAll () {
        //校验参数
        //todo 当前情况下，查询列表暂无参数需要

        //检验通过打印参数
        logger.info("get all productA info start...");
        List<ProductA> data = mapper.selectList(null);
        logger.info("get all productA info end, dataSize:{}", data.size());

        return data;
    }

    public long add (ProductA productA) {
        //校验参数
        notNull(productA, "param.is.null");
        notEmpty(productA.getName(), "name.is.null");
        notNegativeInteger(productA.getPrice(), "price.is.null");

        //检验通过打印参数
        logger.info("add producta:" + productA);
        int newId = mapper.insert(productA);

        //增加或修改操作，需要打印操作结果
        logger.info("add producta success, id:{}", newId);

        return newId;
    }


}
