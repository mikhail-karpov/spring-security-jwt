package com.mikhailkarpov.demo.service;

import com.mikhailkarpov.demo.dto.AuthenticationRequest;
import com.mikhailkarpov.demo.dto.AuthenticationResponse;

public interface AuthService {

    AuthenticationResponse getAccessToken(AuthenticationRequest authRequest);
}
