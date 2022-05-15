package com.mark.cheng.service;

import com.mark.cheng.entity.User;

import java.util.List;

/**
 * @author cheng
 * @since 2022/5/15 00:25
 **/
public interface UserService {

    List<User> findAll();

    int insert(User user);

    int update(User user);

    int delete(int id);

    List<User> selectPage(int pageNum, int pageSize, String username);

    int selectPageTotal(String username);
}
