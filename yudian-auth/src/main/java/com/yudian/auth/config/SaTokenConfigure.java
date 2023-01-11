package com.yudian.auth.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * 注册Sa-Token的注解拦截器，打开注解式鉴权功能
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("注册SA-Token");
        // 注册Sa-Token的路由拦截器
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/public/**",
                        "/**/public/**",
                        "/lot/**",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/*.ico",
                        "/**/*.js",
                        "/*/api-docs",
                        "/swagger-resources/**",
                        "/druid/**"
                );
    }

    /**
     * Sa-Token 整合 jwt
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

}

