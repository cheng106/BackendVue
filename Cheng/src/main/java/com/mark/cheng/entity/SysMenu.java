package com.mark.cheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 *
 * @author Cheng®
 * @since 2022-07-12
 */
@Getter
@Setter
@TableName("sys_menu")
@ApiModel(value = "SysMenu物件", description = "")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String path;

    private String icon;

    private String description;

    private Integer pid;

    @TableField(exist = false)
    private List<SysMenu> children;


}
