package com.mark.cheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author cheng
 * @since 2022/5/15 00:06
 **/
@Data
@ToString
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("帳號")
    private String username;

    @JsonIgnore
    private String password;

    @ApiModelProperty("暱稱")
    private String nickname;

    @ApiModelProperty("性別")
    private Integer gender;

    @ApiModelProperty("電子信箱")
    private String email;

    @ApiModelProperty("手機")
    private String phone;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("大頭照")
    private String avatar;

    @ApiModelProperty("建立時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("角色")
    private String role;
}
