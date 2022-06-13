package com.mark.cheng.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mark.cheng.entity.SysUser;
import com.mark.cheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author cheng
 * @since 2022/6/4 13:37
 **/
@Slf4j
@Component
public class TokenUtils {

    private static UserService staticUserService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void setStaticUserService() {
        staticUserService = userService;
    }

    public static String getToken(String userId, String signature) {
        return JWT.create().withAudience(userId) // 將user id存到token (payload)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小時後token過期
                .sign(Algorithm.HMAC256(signature));
    }

    public static SysUser getCurrentUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.warn("not found request attribute");
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            try {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(userId);
            } catch (Exception e) {
                log.error("ERR:{}", e.getMessage(), e);
                return null;
            }
        }
        return null;
    }
}
