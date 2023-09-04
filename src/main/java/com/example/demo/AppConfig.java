package com.example.demo;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.config")
@Builder
public record AppConfig(String name, String email, int retryNumber, String logout,
                        String keycloakTokenEndpoint,
                        String keycloakClientId,
                        String keycloakClientSecret
) {
}
