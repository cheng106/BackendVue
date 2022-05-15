package com.mark.cheng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author cheng
 * @since 2022/5/15 14:22
 **/
@Configuration
public class CorsConfig {
    // Access-Control-Max-Age 則是告訴瀏覽器幾秒之內不用再次預檢，
    // 以 86400 來說，就是完成一次預檢後有一天的效力。
    private static final long MAX_AGE = 60 * 60 * 24;

    private CorsConfiguration buildConfig() {
        CorsConfiguration cc = new CorsConfiguration();
        // 設定請求原地址 http://localhost:8080/
        cc.addAllowedOrigin("*");
        // 設定請求標頭
        cc.addAllowedHeader("*");
        // 設定請求方法
        cc.addAllowedMethod("*");
        cc.setMaxAge(MAX_AGE);
        return cc;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
