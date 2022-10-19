package com.mark.cheng.service;

import com.mark.cheng.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 服務類別
 *
 * @author Cheng®
 * @since 2022-07-12
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findMenus(String name);

}
