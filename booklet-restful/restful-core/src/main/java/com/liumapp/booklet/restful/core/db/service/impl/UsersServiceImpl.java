package com.liumapp.booklet.restful.core.db.service.impl;

import com.liumapp.booklet.restful.core.db.entity.Users;
import com.liumapp.booklet.restful.core.db.mapper.UsersMapper;
import com.liumapp.booklet.restful.core.db.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liumapp
 * @since 2019-07-24
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
