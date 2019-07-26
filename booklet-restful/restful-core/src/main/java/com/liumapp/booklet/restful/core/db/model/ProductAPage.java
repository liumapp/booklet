package com.liumapp.booklet.restful.core.db.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * file ProductAPage.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProductAPage<T> extends Page<T> {

    private static final long serialVersionUID = -6323952395722797740L;

    private Integer selectInt;

    private String selectStr;

    private String name;

    public ProductAPage(long current, long size) {
        super(current, size);
    }
}
