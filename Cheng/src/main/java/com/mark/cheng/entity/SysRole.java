package com.mark.cheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Cheng®
 * @since 2022-07-07
 */
@Getter
@Setter
@TableName("sys_role")
@ApiModel(value = "SysRole物件", description = "")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色名稱")
    private String name;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("辨別角色:ROLE_***")
    private String roleCode;


}
