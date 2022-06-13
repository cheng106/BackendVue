package com.mark.cheng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mark.cheng.entity.SysUser;
import org.apache.ibatis.annotations.*;

/**
 * @author cheng
 * @since 2022/5/15 00:15
 **/
public interface UserMapper extends BaseMapper<SysUser> {

    @Insert("INSERT INTO sys_user (USERNAME, PASSWORD, NICKNAME, GENDER, EMAIL, PHONE, ADDRESS, CREATE_TIME) " +
            " VALUES (#{username}, #{password}, #{nickname}, #{gender}, #{email}, #{phone}, #{address}, NOW())")
    int insert(SysUser sysUser);

    int update(SysUser sysUser);

    /* Cannot override the same method name of the parent class */
//    @Select("SELECT * FROM sys_user WHERE username LIKE CONCAT('%', #{username}, '%') LIMIT ${pageNum}, ${pageSize}")
//    List<User> selectPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, @Param("username") String username);

}
