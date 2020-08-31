package com.mikhailkarpov.demo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(String username);

    String validateAndGetUsername(String jwt) throws AuthenticationException;
}
