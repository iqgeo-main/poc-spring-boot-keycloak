package com.example.demo;

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

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

    private final OAuth2AuthorizedClientService authorizedClientService;

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
        return client.getAccessToken().getTokenValue();
    }

}
