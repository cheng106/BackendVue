package com.mark.cheng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 *
 * @author Cheng®
 * @since 2022-07-26
 */
@Getter
@Setter
@TableName("sys_dict")
@ApiModel(value = "SysDict物件", description = "")
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String value;

    private String type;


}
