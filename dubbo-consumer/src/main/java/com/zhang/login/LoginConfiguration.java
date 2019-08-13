package com.zhang.login;

import com.zhang.interceptor.LoginInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class LoginConfiguration implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {

        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);

        // 拦截路径
        loginRegistry.addPathPatterns("/**");
        // 排除路径，不拦截
        loginRegistry.excludePathPatterns("/user/toLogin");

        loginRegistry.excludePathPatterns("/user/gainCode");

        loginRegistry.excludePathPatterns("/user/login");

        // 排除资源请求

        loginRegistry.excludePathPatterns("/css/login/*.css");

        loginRegistry.excludePathPatterns("/js/login/**/*.js");

        loginRegistry.excludePathPatterns("/image/login/*.png");
    }
}
