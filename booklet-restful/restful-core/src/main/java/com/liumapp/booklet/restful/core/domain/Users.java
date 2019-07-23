package com.liumapp.booklet.restful.core.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * file Users.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/23
 */
@Data
public class Users implements Serializable {

    private static final long serialVersionUID = -1038399311579337157L;

    private String name;

    private String phone;

    private String appId;

    private String appSecret;

    private String lastToken;

}
