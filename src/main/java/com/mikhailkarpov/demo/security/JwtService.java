package com.mikhailkarpov.demo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String getUsername(String jwt) throws AuthenticationException;

    boolean isValid(String jwt, UserDetails userDetails);
}
