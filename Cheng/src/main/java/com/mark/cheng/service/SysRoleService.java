package com.mark.cheng.service;

import com.mark.cheng.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 服務類別
 *
 * @author Cheng®
 * @since 2022-07-07
 */
public interface SysRoleService extends IService<SysRole> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
