package com.example.demo;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

@Slf4j
public class Oauth2InterceptionFilter implements Filter {

    private CodeProcessingService codeProcessingService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        codeProcessingService = applicationContext.getBean(CodeProcessingService.class);
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String session = request.getParameter("session_state");

        if (!ObjectUtils.isEmpty(code)) {
           log.info("Captured OAuth2 code {}", code);
           codeProcessingService.processCode(code);
        }

        if (!ObjectUtils.isEmpty(state)) {
            log.info("Captured OAuth2 state {}", state);
            codeProcessingService.processState(state);
        }

        if (!ObjectUtils.isEmpty(session)) {
            log.info("Captured OAuth2 session_state {}", session);
            codeProcessingService.processSession(session);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
