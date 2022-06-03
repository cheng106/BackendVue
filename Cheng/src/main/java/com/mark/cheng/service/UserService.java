package com.mark.cheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mark.cheng.controller.dto.UserDto;
import com.mark.cheng.entity.User;

/**
 * @author cheng
 * @since 2022/5/15 00:25
 **/
public interface UserService extends IService<User> {
    UserDto login(UserDto userDto);

    boolean register(UserDto userDto);
}
