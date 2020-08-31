package com.mikhailkarpov.demo.controller;

import com.mikhailkarpov.demo.dto.AuthenticationRequest;
import com.mikhailkarpov.demo.dto.AuthenticationResponse;
import com.mikhailkarpov.demo.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/access_token")
    public AuthenticationResponse getJwt(@Valid @RequestBody AuthenticationRequest request) {
        return authService.getAccessToken(request);
    }
}
