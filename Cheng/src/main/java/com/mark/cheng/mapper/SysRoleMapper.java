package com.mark.cheng.mapper;

import com.mark.cheng.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Mapper 介面
 *
 * @author Cheng®
 * @since 2022-07-07
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select id from sys_role where role_code = #{role}")
    Integer selectByRoleCode(@Param("role") String role);
}
