package com.mark.cheng.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.cheng.controller.dto.UserDto;
import com.mark.cheng.entity.User;
import com.mark.cheng.enums.ApiResultStatus;
import com.mark.cheng.exception.BizException;
import com.mark.cheng.model.R;
import com.mark.cheng.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    @PostMapping("register")
    public R register(@RequestBody UserDto userDto) {
        String userName = userDto.getUsername();
        String pwd = userDto.getPassword();
        if (StrUtil.isAllBlank(userName, pwd)) {
            return R.failed(ApiResultStatus.PARAM_ERROR);
        }
        try {
            boolean result = userService.register(userDto);
            if (result) {
                return R.success();
            } else {
                return R.failed(ApiResultStatus.ERROR);
            }
        } catch (BizException e) {
            return R.failed(e);
        }
    }

    @PostMapping("login")
    public R login(@RequestBody UserDto userDto) {
        log.info("user:{}", userDto);
        String userName = userDto.getUsername();
        String pwd = userDto.getPassword();
        if (StrUtil.isAllBlank(userName, pwd)) {
            return R.failed(ApiResultStatus.PARAM_ERROR);
        }
        try {
            UserDto loginUser = userService.login(userDto);
            if (loginUser != null) {
                return R.success(loginUser);
            } else {
                return R.failed(ApiResultStatus.USER_NOT_FOUND);
            }
        } catch (BizException e) {
            return R.failed(e);
        }

    }

    @PostMapping
    @PutMapping
    @ApiOperation("儲存或更新使用者")
    public boolean saveOrUpdateUser(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("{id}")
    @ApiOperation("刪除使用者")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @GetMapping("username/{name}")
    @ApiOperation("取得使用者訊息")
    public R delete(@PathVariable String name) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, name);
        return R.success(userService.getOne(wrapper));
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }

    @GetMapping("page")
    @ApiOperation("使用者管理-分頁查詢")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam String username,
                                @RequestParam String nickname,
                                @RequestParam String email) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(User::getUsername, username)
                .like(User::getNickname, nickname)
                .like(User::getEmail, email)
                .orderByDesc(User::getCreateTime);
        return userService.page(page, wrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        List<User> list = userService.list();
        try (ServletOutputStream out = response.getOutputStream();
             ExcelWriter writer = ExcelUtil.getWriter(true)) {

            writer.addHeaderAlias("username", "使用者名稱");
            writer.addHeaderAlias("nickname", "暱稱");
            writer.addHeaderAlias("gender", "性別");
            writer.addHeaderAlias("email", "信箱");
            writer.addHeaderAlias("phone", "手機");
            writer.addHeaderAlias("address", "地址");
            writer.addHeaderAlias("createTime", "建立時間");

            writer.write(list, true);

            String fileName = "使用者資訊";
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()) + ".xlsx");

            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/import")
    public Boolean importFile(@RequestBody MultipartFile file) {
        try (ExcelReader reader = ExcelUtil.getReader(file.getInputStream())) {
            List<User> list = reader.readAll(User.class);
            log.info("list:{}", list);
            return userService.saveBatch(list);
        } catch (IOException e) {
            log.error("ERR:", e);
            return false;
        }

    }
}
