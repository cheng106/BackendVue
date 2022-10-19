package com.mark.cheng.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.cheng.controller.dto.UserDto;
import com.mark.cheng.entity.SysMenu;
import com.mark.cheng.entity.SysUser;
import com.mark.cheng.enums.ApiResultStatus;
import com.mark.cheng.exception.BizException;
import com.mark.cheng.mapper.SysRoleMapper;
import com.mark.cheng.mapper.SysRoleMenuMapper;
import com.mark.cheng.mapper.UserMapper;
import com.mark.cheng.service.SysMenuService;
import com.mark.cheng.service.UserService;
import com.mark.cheng.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cheng
 * @since 2022/5/21 14:57
 **/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private SysMenuService sysMenuService;

    @Override
    public UserDto login(UserDto userDto) {
        return getUserInfo(userDto);
    }

    @Override
    public boolean register(UserDto userDto) {
        UserDto getUserDto = getUserInfo(userDto);
        if (getUserDto == null) {
            SysUser sysUser = new SysUser();
            BeanUtil.copyProperties(userDto, sysUser, true);
            return save(sysUser);
        } else {
            throw BizException.create(ApiResultStatus.USER_EXISTS);
        }
    }

    private UserDto getUserInfo(UserDto userDto) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, userDto.getUsername())
                .eq(SysUser::getPassword, userDto.getPassword());
        try {
            SysUser one = getOne(wrapper);
            if (one != null) {
                BeanUtil.copyProperties(one, userDto, true);
                // 設定token
                String token = TokenUtils.getToken(String.valueOf(one.getId()), one.getPassword());
                userDto.setToken(token);

                String role = one.getRole();
                log.info("-=-=--------------role:{}", role);

                // 設定使用者的選單列表
                userDto.setMenus(this.getRoleMenus(role));
                return userDto;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("ERR:", e);
            throw BizException.create(ApiResultStatus.SYSTEM_ERROR, e);
        }
    }


    /**
     * 取得目前角色的選單列表
     *
     * @param role role code
     * @return java.util.List<com.mark.cheng.entity.SysMenu>
     **/
    private List<SysMenu> getRoleMenus(String role) {
        Integer roleId = sysRoleMapper.selectByRoleCode(role);
        List<Integer> menuIds = sysRoleMenuMapper.selectByRoleId(roleId);

        // 查出系統所有選單
        List<SysMenu> menus = sysMenuService.findMenus("");

        // 用一個list存篩選最終結果
        List<SysMenu> roleMenus = new ArrayList<>();

        // 篩選目前使用者角色的選單
        menus.forEach(m -> {
            if (menuIds.contains(m.getId())) {
                roleMenus.add(m);
            }
            List<SysMenu> children = m.getChildren();
            // removeIf 移除 children 裡面不在 menuIds 集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        });

        return roleMenus;
    }
}
