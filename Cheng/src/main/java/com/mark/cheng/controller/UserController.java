package com.mark.cheng.controller;

import com.mark.cheng.entity.User;
import com.mark.cheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author cheng
 * @since 2022/5/14 23:36
 **/
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public int save(@RequestBody User user) {
        return userService.insert(user);
    }

    @PutMapping
    public int update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("{id}")
    public int delete(@PathVariable int id) {
        return userService.delete(id);
    }

    @GetMapping("page")
    public Object findPage(@RequestParam int pageNum,
                           @RequestParam int pageSize,
                           @RequestParam String username) {
        pageNum = (pageNum - 1) * pageSize;
        Integer total = userService.selectPageTotal(username);
        List<User> users = userService.selectPage(pageNum, pageSize, username);
        return new HashMap<String, Object>() {{
            put("total", total);
            put("users", users);
        }};
    }
}
