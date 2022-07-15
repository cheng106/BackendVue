package com.mark.cheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.cheng.model.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.mark.cheng.service.SysRoleService;
import com.mark.cheng.entity.SysRole;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cheng®
 * @since 2022-07-07
 */
@Slf4j
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping
    public R save(@RequestBody SysRole sysRole) {
        return R.success(sysRoleService.saveOrUpdate(sysRole));
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        return R.success(sysRoleService.removeById(id));
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return sysRoleService.removeByIds(ids);
    }

    @GetMapping
    public List<SysRole> findAll() {
        return sysRoleService.list();
    }

    @GetMapping("/{id}")
    public SysRole findOne(@PathVariable Integer id) {
        return sysRoleService.getById(id);
    }

    @GetMapping("/page")
    public R findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String name) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(SysRole::getName, name).orderByDesc(SysRole::getId);
        Page<SysRole> page = sysRoleService.page(new Page<>(pageNum, pageSize), wrapper);
        return R.success(page);
    }

}
