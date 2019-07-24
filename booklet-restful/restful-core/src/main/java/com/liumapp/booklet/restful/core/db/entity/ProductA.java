package com.liumapp.booklet.restful.core.db.entity;

import com.liumapp.booklet.restful.core.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liumapp
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("product_a")
@ApiModel(value="ProductA对象", description="")
public class ProductA extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer price;


}
