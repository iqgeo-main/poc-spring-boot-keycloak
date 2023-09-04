package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CustomAuthController {

    private final AppConfig appConfig;

    @PostMapping("/auth/token")
    public ResponseEntity<String> exchangeCodeForToken(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", appConfig.keycloakClientId());
        map.add("client_secret", appConfig.keycloakClientSecret());
        map.add("code", code);
        map.add("redirect_uri", "http://localhost:3000/");

        ResponseEntity<String> response = restTemplate.postForEntity(appConfig.keycloakTokenEndpoint(), map, String.class);

        return response;
    }
}

