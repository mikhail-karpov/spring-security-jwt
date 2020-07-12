package com.mikhailkarpov.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSettings {

    @Value("${jwt.issuer")
    private String issuer;

    @Value("${jwt.secret")
    private String secret;

    @Value("${jwt.expiresInMin}")
    private int expiresInMin;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getExpiresInMin() {
        return expiresInMin;
    }

    public void setExpiresInMin(int expiresInMin) {
        this.expiresInMin = expiresInMin;
    }
}
