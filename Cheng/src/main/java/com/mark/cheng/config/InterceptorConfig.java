package com.mark.cheng.config;

import com.mark.cheng.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author cheng
 * @since 2022/6/4 14:16
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> ignorePaths = Arrays.asList(
                "/sysUser/login",
                "/sysUser/register",
                "/**/export",
                "/**/import",
                "/file/**",
                "/test/**",
                "/favicon.ico");

        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 攔截所有請求，通過判斷token是否合法來決定要不要登入
                .excludePathPatterns(ignorePaths);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
