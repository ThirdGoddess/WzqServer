package com.demo.wzq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/4/4 16:37
 * @desc
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加Web项目的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }
}
