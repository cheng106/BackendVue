package com.mark.cheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Cheng®
 * @since 2022-06-05
 */
@Getter
@Setter
@TableName("sys_file")
@ToString
@ApiModel(value = "SysFile物件", description = "檔案管理")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String type;

    @ApiModelProperty("單位:KB")
    private Long size;

    private String url;

    private String md5;

    @ApiModelProperty("是否刪除")
    private Boolean isDelete;

    @ApiModelProperty("是否啟用")
    private Boolean isEnable;


}
