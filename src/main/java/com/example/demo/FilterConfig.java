package com.example.demo;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Oauth2InterceptionFilter> loggingFilter() {
        FilterRegistrationBean<Oauth2InterceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Oauth2InterceptionFilter());
        registrationBean.addUrlPatterns("/oauth2/authorization/keycloak");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Oauth2InterceptionFilter> oauth2CodeFilter() {
        FilterRegistrationBean<Oauth2InterceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Oauth2InterceptionFilter());
        registrationBean.addUrlPatterns("/login/oauth2/code/keycloak");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        return registrationBean;
    }

}

