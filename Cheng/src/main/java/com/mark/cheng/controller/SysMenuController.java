package com.mark.cheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.cheng.common.Constants;
import com.mark.cheng.entity.SysDict;
import com.mark.cheng.model.R;
import com.mark.cheng.service.SysDictService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.mark.cheng.service.SysMenuService;
import com.mark.cheng.entity.SysMenu;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cheng®
 * @since 2022-07-12
 */
@Slf4j
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysDictService sysDictService;

    @PostMapping
    public R save(@RequestBody SysMenu sysMenu) {
        return R.success(sysMenuService.saveOrUpdate(sysMenu));
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        return R.success(sysMenuService.removeById(id));
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return sysMenuService.removeByIds(ids);
    }

    @GetMapping
    public R findAll(@RequestParam(defaultValue = "") String name) {
        return R.success(sysMenuService.findMenus(name));
    }

    @GetMapping("/{id}")
    public SysMenu findOne(@PathVariable Integer id) {
        return sysMenuService.getById(id);
    }

    @GetMapping("/page")
    public R findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String name) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        // sql where condition
        wrapper.like(SysMenu::getName, name).orderByDesc(SysMenu::getId);
        Page<SysMenu> page = sysMenuService.page(new Page<>(pageNum, pageSize), wrapper);
        return R.success(page);
    }

    @GetMapping("icons")
    public R getIcons() {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getType, Constants.DICT_TYPE_ICON);
        return R.success(sysDictService.list(wrapper));
    }

}
