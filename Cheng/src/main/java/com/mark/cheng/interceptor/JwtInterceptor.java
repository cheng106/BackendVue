package com.mark.cheng.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.mark.cheng.entity.SysUser;
import com.mark.cheng.enums.ApiResultStatus;
import com.mark.cheng.exception.BizException;
import com.mark.cheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cheng
 * @since 2022/6/4 14:00
 **/
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        // 如果不是對應到方法則直接通過
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (StringUtils.isBlank(token)) {
            throw BizException.create(ApiResultStatus.TOKEN_VALIDATION_FAILED);
        }

        // 取得token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            log.error("ERR:{}", e.getMessage(), e);
            throw BizException.create(ApiResultStatus.TOKEN_VALIDATION_FAILED);
        }
        SysUser sysUser = userService.getById(userId);
        if (sysUser == null) {
            throw BizException.create(ApiResultStatus.USER_NOT_FOUND);
        }

        // 將使用者密碼簽章後驗證
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(sysUser.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw BizException.error(e, ApiResultStatus.ERROR);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
