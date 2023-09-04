package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public OAuth2LoginConfigurer<HttpSecurity> oauth2LoginConfigurer() {
        return (OAuth2LoginConfigurer<HttpSecurity>) new OAuth2LoginConfigurer<>()
                .successHandler(myAuthenticationSuccessHandler());
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                log.info("onAuthenticationSuccess ");

                Enumeration<String> headerNames = request.getHeaderNames();

                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);
                    log.info("request {} {}", headerName, headerValue);
                }

                response.getHeaderNames().forEach(s -> {
                    String headerValue = response.getHeader(s);
                    log.info("response {} {}", s, headerValue);

                });

                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }
}
