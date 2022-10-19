package com.mark.cheng.controller.dto;

import com.mark.cheng.entity.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * @author cheng
 * @since 2022/5/23 22:40
 **/
@Data
public class UserDto {
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String token;
    private String role;
    private List<SysMenu> menus;
}
