package com.mark.cheng.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author cheng
 * @since 2022/5/15 23:07
 **/
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Value("${swagger.enabled}")
    private boolean enabled;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                // 是否開啟Swagger
                .enable(enabled)
                // 建立api基本資料，顯示在頁面中
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                // 選擇哪些api顯示
                .select()
                // 有此註解的api
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))

                // 指定package中的api
                // .apis(RequestHandlerSelectors.basePackage("com.ruoyi.project.tool.swagger"))

                // 任何的api
                .apis(RequestHandlerSelectors.any())

                .paths(PathSelectors.any())
                // 忽略以"/error"開頭的路徑,可以防止顯示如404錯誤
                .paths(PathSelectors.regex("/error.*").negate())
                // 忽略以"/actuator"開頭的路徑
                .paths(PathSelectors.regex("/actuator.*").negate())
                .build();
    }

    /**
     * http://localhost:9090/swagger-ui/index.html
     *
     * @return springfox.documentation.service.ApiInfo
     **/
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("title:管理系統後台")
                .description("description:練習用")
                .contact(new Contact("Cheng®", null, null))
                .version("version:" + 0.1)
                .build();
    }
}
