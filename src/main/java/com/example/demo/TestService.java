package com.example.demo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestService {

    private final AppConfig appConfig;

    @PostConstruct
    public void init() {
        log.info("Loading configuration");
        log.info("name {}", appConfig.name());
        log.info("email {}", appConfig.email());
        log.info("retry_number {}", appConfig.retryNumber());
        log.info("logout_url {}", appConfig.logout());
    }

}
