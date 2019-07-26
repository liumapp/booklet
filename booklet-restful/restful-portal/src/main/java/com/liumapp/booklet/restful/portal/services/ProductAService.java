package com.liumapp.booklet.restful.portal.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liumapp.booklet.restful.core.db.entity.ProductA;
import com.liumapp.booklet.restful.core.db.mapper.ProductAMapper;
import com.liumapp.booklet.restful.core.db.service.impl.ProductAServiceImpl;
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


    public IPage<ProductA> selectUserPage(Page<ProductA> page) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return mapper.selectPageVo(page);
    }

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
