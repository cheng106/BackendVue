package com.mark.cheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.cheng.entity.User;
import com.mark.cheng.mapper.UserMapper;
import com.mark.cheng.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author cheng
 * @since 2022/5/21 14:57
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
