package com.mikhailkarpov.demo.controller;

import com.mikhailkarpov.demo.dto.AuthenticationRequest;
import com.mikhailkarpov.demo.dto.AuthenticationResponse;
import com.mikhailkarpov.demo.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtService jwtService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserDetailsService userDetailsService,
                                    JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @GetMapping("/access_token")
    public ResponseEntity<Object> getJwt(@Valid @RequestBody AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        authenticate(username, password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    private Authentication authenticate(String username, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
