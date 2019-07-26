package com.liumapp.booklet.restful.core.db.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liumapp.booklet.restful.core.db.entity.ProductA;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liumapp.booklet.restful.core.db.model.ProductAPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liumapp
 * @since 2019-07-24
 */
public interface ProductAMapper extends BaseMapper<ProductA> {

    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * 注意!!: 如果入参是有多个,需要加注解指定参数名才能在xml中取值
     * </p>
     *
     * @return 分页对象
     */
    ProductAPage<ProductA> mySelectPage(@Param("pg") ProductAPage<ProductA> myPage);

}
