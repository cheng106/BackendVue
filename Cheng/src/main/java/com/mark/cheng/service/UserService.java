package com.mark.cheng.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.cheng.entity.User;
import com.mark.cheng.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author cheng
 * @since 2022/5/15 00:25
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public boolean saveOrUpdateUser(User user) {
        return saveOrUpdate(user);
    }
}
