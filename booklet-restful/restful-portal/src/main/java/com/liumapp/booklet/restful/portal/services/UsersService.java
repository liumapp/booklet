package com.liumapp.booklet.restful.portal.services;

import com.liumapp.booklet.restful.core.db.entity.Users;
import com.liumapp.booklet.restful.portal.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.liumapp.booklet.restful.core.util.CheckUtil.*;
/**
 * file UsersService.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/27
 */
@Service
public class UsersService {

    @Autowired
    private TokenUtil tokenUtil;

    public String generateUserToken (Users users) {
        notNull(users, "param.is.nul");
        notNull(users.getPhone(), "phone.is.null");

        //检查用户账号是否存在
        // todo

        String token = tokenUtil.generateToken(users);
        return token;
    }

    public Boolean saveUserInfoToThreadLocal (Users users) {

        return false;
    }

}
