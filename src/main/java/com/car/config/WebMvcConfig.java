package com.car.config;

import com.car.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 15:12
 * @FileName: WebMvcConfig.java
 * @Desc    : Web配置
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                // 拦截所有请求路径
                .addPathPatterns("/**")
                // 不需要拦截的请求路径
                .excludePathPatterns("/user/login",
                        "/user/register",
                        "/captcha",
                        "/doc.html",
                        "/swagger-resources",
                        "/v2/api-docs");
    }

    /**
     * 统一跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 是否发送Cookie
                .allowCredentials(true)
                // 支持域
                .allowedOriginPatterns("*")
                // 支持方法
                .allowedMethods("*")
                // 支持头
                .allowedHeaders("*");
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
