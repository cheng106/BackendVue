package com.mark.cheng.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.cheng.controller.dto.UserDto;
import com.mark.cheng.entity.User;
import com.mark.cheng.enums.ApiResultStatus;
import com.mark.cheng.exception.BizException;
import com.mark.cheng.mapper.UserMapper;
import com.mark.cheng.service.UserService;
import com.mark.cheng.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author cheng
 * @since 2022/5/21 14:57
 **/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public UserDto login(UserDto userDto) {
        return getUserInfo(userDto);
    }

    @Override
    public boolean register(UserDto userDto) {
        UserDto getUserDto = getUserInfo(userDto);
        if (getUserDto == null) {
            User user = new User();
            BeanUtil.copyProperties(userDto, user, true);
            return save(user);
        } else {
            throw BizException.create(ApiResultStatus.USER_EXISTS);
        }
    }

    private UserDto getUserInfo(UserDto userDto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userDto.getUsername())
                .eq(User::getPassword, userDto.getPassword());
        try {
            User one = getOne(wrapper);
            if (one != null) {
                BeanUtil.copyProperties(one, userDto, true);
                // 設定token
                String token = TokenUtils.getToken(String.valueOf(one.getId()), one.getPassword());
                userDto.setToken(token);
                return userDto;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("ERR:", e);
            throw BizException.create(ApiResultStatus.SYSTEM_ERROR, e);
        }
    }
}
