package com.mark.cheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.cheng.model.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.mark.cheng.service.SysDictService;
import com.mark.cheng.entity.SysDict;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author Cheng®
 * @since 2022-07-26
 */
@Slf4j
@RestController
@RequestMapping("/sys-dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @PostMapping
    public R save(@RequestBody SysDict sysDict) {
        return R.success(sysDictService.saveOrUpdate(sysDict));
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        return R.success(sysDictService.removeById(id));
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return sysDictService.removeByIds(ids);
    }

    @GetMapping
    public List<SysDict> findAll() {
        return sysDictService.list();
    }

    @GetMapping("/{id}")
    public SysDict findOne(@PathVariable Integer id) {
        return sysDictService.getById(id);
    }

    @GetMapping("/page")
    public R findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        // TODO: sql where condition
        Page<SysDict> page = sysDictService.page(new Page<>(pageNum, pageSize), wrapper);
        return R.success(page);
    }

}
