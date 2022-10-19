package com.mark.cheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mark.cheng.entity.SysMenu;
import com.mark.cheng.mapper.SysMenuMapper;
import com.mark.cheng.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 服務實現類別
 *
 * @author Cheng®
 * @since 2022-07-12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> findMenus(String name) {
        // 查資料
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(SysMenu::getName, name);
        }
        List<SysMenu> menuList = list(wrapper);

        // 找出第一層選單(pid == null)
        List<SysMenu> parentMenus = menuList.stream()
                .filter(m -> m.getPid() == null)
                .collect(Collectors.toList());

        // 找出子選單
        parentMenus.forEach(menu -> {
            List<SysMenu> childrenList = menuList.stream()
                    // 篩選所有資料中pid為父級id的資料就是第二層選單
                    .filter(m -> Objects.equals(menu.getId(), m.getPid()))
                    .collect(Collectors.toList());
            menu.setChildren(childrenList);
        });

        return parentMenus;
    }
}
