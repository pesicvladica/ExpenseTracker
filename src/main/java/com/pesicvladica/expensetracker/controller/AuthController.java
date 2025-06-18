package com.pesicvladica.expensetracker.controller;

import com.pesicvladica.expensetracker.dto.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.UserResponse;
import com.pesicvladica.expensetracker.service.AuthService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // region Parameters

    private final AuthService authService;

    // endregion

    // region Initialization

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // endregion

    // region API Endpoints

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok().body(authService.register(userRegisterRequest));
    }

    // endregion
}
