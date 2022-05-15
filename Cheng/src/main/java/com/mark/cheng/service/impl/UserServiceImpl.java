package com.mark.cheng.service.impl;

import com.mark.cheng.entity.User;
import com.mark.cheng.mapper.UserMapper;
import com.mark.cheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cheng
 * @since 2022/5/15 00:26
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public int insert(User user) {
        user.setCreateTime(LocalDateTime.now());
        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int delete(int id) {
        return userMapper.deleteById(id);
    }

    @Override
    public List<User> selectPage(int pageNum, int pageSize, String username) {
        return userMapper.selectPage(pageNum, pageSize, username);
    }

    @Override
    public int selectPageTotal(String username) {
        return userMapper.selectPageTotal(username);
    }

}
