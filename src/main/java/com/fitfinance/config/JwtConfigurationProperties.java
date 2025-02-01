package com.fitfinance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt-config")
public record JwtConfigurationProperties(String secretKey, long tokenExpiration, long refreshTokenExpiration) {
}
