package com.fitfinance.authentication.controller;

import com.fitfinance.authentication.request.AuthenticationRequest;
import com.fitfinance.authentication.request.RegisterRequest;
import com.fitfinance.authentication.service.AuthenticationService;
import com.fitfinance.backend.domain.User;
import com.fitfinance.backend.mapper.UserMapper;
import com.fitfinance.backend.response.UserGetResponse;
import com.fitfinance.commons.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<UserGetResponse> register(@RequestBody RegisterRequest request) {
        User registered = service.register(request);
        UserGetResponse userGetResponse = mapper.toUserGetResponse(registered);
        return ResponseEntity.status(HttpStatus.CREATED).body(userGetResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }
}