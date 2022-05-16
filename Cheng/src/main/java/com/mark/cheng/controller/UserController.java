package com.mark.cheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.cheng.entity.User;
import com.mark.cheng.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cheng
 * @since 2022/5/14 23:36
 **/
@Slf4j
@RestController
@Api("使用者功能")
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @PutMapping
    @ApiOperation("儲存或更新使用者")
    public boolean saveOrUpdateUser(@RequestBody User user) {
        return userService.saveOrUpdateUser(user);
    }

    @DeleteMapping("{id}")
    @ApiOperation("刪除使用者")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    // 使用Mybatis自己實做分頁的寫法
    /*
      @Deprecated
//    @GetMapping("page")
//    public Object findPage(@RequestParam int pageNum,
//                           @RequestParam int pageSize,
//                           @RequestParam String username) {
//        pageNum = (pageNum - 1) * pageSize;
//        Integer total = userService.selectPageTotal(username);
//        List<User> users = userService.selectPage(pageNum, pageSize, username);
//        return new HashMap<String, Object>() {{
//            put("total", total);
//            put("users", users);
//        }};
//    }
     */

    @GetMapping("page")
    @ApiOperation("使用者管理-分頁查詢")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam String username) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(User::getUsername, username);
        return userService.page(page, wrapper);
    }
}
