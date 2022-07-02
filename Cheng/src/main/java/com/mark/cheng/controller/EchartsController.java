package com.mark.cheng.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.mark.cheng.entity.SysUser;
import com.mark.cheng.model.R;
import com.mark.cheng.service.UserService;
import com.mark.cheng.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author cheng
 * @since 2022/7/2 18:37
 **/
@RestController
@RequestMapping("echarts")
public class EchartsController {

    @Autowired
    private UserService userService;

    @GetMapping("example")
    public R get() {
        Map<String, Object> dataMap = new HashMap<String, Object>() {{
            put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
            put("y", CollUtil.newArrayList(150, 230, 224, 123, 445, 23, 145));
        }};
        return R.success(dataMap);
    }

    @GetMapping("members")
    public R members() {
        List<SysUser> users = userService.list();
        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
        for (SysUser user : users) {
            LocalDateTime createTime = user.getCreateTime();
            Date date = DateUtils.LdtToDate(createTime);
            Quarter quarter = DateUtil.quarterEnum(date);
            switch (quarter) {
                case Q1:
                    q1 += 1;
                    break;
                case Q2:
                    q2 += 1;
                    break;
                case Q3:
                    q3 += 1;
                    break;
                case Q4:
                    q4 += 1;
                    break;
                default:
                    // do nothing
                    break;
            }
        }
        return R.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

}
