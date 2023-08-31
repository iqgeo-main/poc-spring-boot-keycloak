package com.example.demo;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;


@Slf4j
@Getter
@Component
public class CustomWebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        //scoatem inforamtii despre header etc
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        StringBuilder requestBuilder = new StringBuilder();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpRequest.getHeader(headerName);
            requestBuilder.append(" - ").append(headerName).append(" : ").append(headerValue).append(" - ");
        }

        log.info(requestBuilder.toString());

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // nu am nevoie sa fac acest destory
        Filter.super.destroy();
    }

}
