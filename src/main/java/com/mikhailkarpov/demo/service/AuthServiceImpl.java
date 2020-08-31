package com.mikhailkarpov.demo.service;

import com.mikhailkarpov.demo.dto.AuthenticationRequest;
import com.mikhailkarpov.demo.dto.AuthenticationResponse;
import com.mikhailkarpov.demo.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private UserDetailsService userDetailsService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserDetailsService userDetailsService,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse getAccessToken(AuthenticationRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        authenticate(username, password);

        String jwt = jwtService.generateToken(username);
        return new AuthenticationResponse(jwt);
    }

    private Authentication authenticate(String username, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
