package com.mark.cheng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mark.cheng.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author cheng
 * @since 2022/5/15 00:15
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM sys_user")
    List<User> findAll();

    @Insert("INSERT INTO sys_user (USERNAME, PASSWORD, NICKNAME, GENDER, EMAIL, PHONE, ADDRESS, CREATE_TIME) " +
            " VALUES (#{username}, #{password}, #{nickname}, #{gender}, #{email}, #{phone}, #{address}, NOW())")
    int insert(User user);

    int update(User user);

    @Select("SELECT * FROM sys_user WHERE username LIKE CONCAT('%', #{username}, '%') LIMIT ${pageNum}, ${pageSize}")
    List<User> selectPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, @Param("username") String username);

    @Select("SELECT COUNT(*) FROM sys_user WHERE username LIKE CONCAT('%', #{username}, '%')")
    Integer selectPageTotal(@Param("username") String username);
}
