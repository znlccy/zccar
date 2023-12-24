package com.car.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:54
 * @FileName: SwaggerConfig.java
 * @Desc    : Swagger配置类
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 生成API文档
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.car"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API文档信息描述
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("餐饮车API接口")
                .description("餐饮车API接口规范")
                .version("1.0")
                .contact(new Contact("znlccy", "http://www.znlccy.com", "znlccy@163.com"))
                .build();
    }
}
