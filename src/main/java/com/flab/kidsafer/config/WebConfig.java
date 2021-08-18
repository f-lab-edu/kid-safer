package com.flab.kidsafer.config;

import com.flab.kidsafer.config.auth.LoginUserArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;
    private final NotificationInterceptor notificationInterceptor;

    public WebConfig(LoginUserArgumentResolver loginUserArgumentResolver, NotificationInterceptor notificationInterceptor) {
        this.loginUserArgumentResolver = loginUserArgumentResolver;
        this.notificationInterceptor = notificationInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolver) {
        argumentResolver.add(loginUserArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(notificationInterceptor)
            .order(1);
    }
}
