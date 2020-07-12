package com.mikhailkarpov.demo.security;

import org.springframework.security.core.AuthenticationException;

public class JwtValidationException extends AuthenticationException {

    public JwtValidationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtValidationException(String msg) {
        super(msg);
    }
}
