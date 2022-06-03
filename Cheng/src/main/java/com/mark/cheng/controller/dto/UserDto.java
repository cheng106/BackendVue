package com.mark.cheng.controller.dto;

import lombok.Data;

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
}
