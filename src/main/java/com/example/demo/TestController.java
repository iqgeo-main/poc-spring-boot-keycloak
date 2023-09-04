package com.example.demo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final CodeProcessingService codeProcessingService;

    @GetMapping("granted/oauth-login")
    public void onTestLogin(@AuthenticationPrincipal OAuth2User principal,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        String token = getSessionBearerToken();

        log.info("oauth-user {} {}", principal, token);

        response.setHeader("my-own-token-header", token);

        Cookie myCookie = new Cookie("my-own-token-cookie", token);
        myCookie.setMaxAge(7 * 24 * 60 * 60); // 7 zile sa traiasca si el linistit
        myCookie.setSecure(true);
//        myCookie.setHttpOnly(true);         daca vreau sa nu apara in javascript
        myCookie.setPath("/");
        response.addCookie(myCookie);

        Cookie myCookieCode = new Cookie("code",codeProcessingService.getCode());
        myCookieCode.setMaxAge(7 * 24 * 60 * 60);
        myCookieCode.setSecure(true);
        myCookieCode.setPath("/");
        response.addCookie(myCookieCode);

        response.sendRedirect("http://localhost:3000");
    }

    @GetMapping("granted/oauth-user")
    public String onTestRequest(@AuthenticationPrincipal OAuth2User principal) {
        log.info("oauth-user {} {}", principal, getSessionBearerToken());
        return "OKI DOKI FROM oauth-user";
    }

    @GetMapping("callback")
    public String onCallbackRequest(
            @RequestParam("state") String state,
            @RequestParam("session_state") String sessionState,
            @RequestParam("code") String code) {
        log.info("onCallbackRequest state {} session_state {}  code {}", state, sessionState, code);
        return "OKI DOKI FROM CALLBACK";
    }

    private String getSessionBearerToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName());

        log.info("principal name {}",client.getPrincipalName());
        log.info("token {}",client.getAccessToken().getTokenValue());
        log.info("refresh token {}", Objects.requireNonNull(client.getRefreshToken()).getTokenValue());

        return client.getAccessToken().getTokenValue();
    }

}
