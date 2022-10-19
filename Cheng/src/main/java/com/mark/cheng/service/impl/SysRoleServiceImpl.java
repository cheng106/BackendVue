package com.mark.cheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.cheng.entity.SysRole;
import com.mark.cheng.entity.SysRoleMenu;
import com.mark.cheng.mapper.SysRoleMapper;
import com.mark.cheng.mapper.SysRoleMenuMapper;
import com.mark.cheng.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服務實現類別
 *
 * @author Cheng®
 * @since 2022-07-07
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        // 先刪除目前角色id所有的綁定關係
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        sysRoleMenuMapper.delete(wrapper);

        // 再把前端綁定的選單id列表，綁定到目前的角色身上
        menuIds.forEach(m -> {
            SysRoleMenu sr = new SysRoleMenu();
            sr.setRoleId(roleId);
            sr.setMenuId(m);
            sysRoleMenuMapper.insert(sr);
        });

        log.info("=== setRoleMenu Success ===");
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return sysRoleMenuMapper.selectByRoleId(roleId);
    }
}
