package com.pesicvladica.expensetracker.controller;

import com.pesicvladica.expensetracker.dto.UserLoginRequest;
import com.pesicvladica.expensetracker.dto.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.UserAuthResponse;
import com.pesicvladica.expensetracker.service.authentication.AuthService;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public ResponseEntity<UserAuthResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok().body(authService.register(userRegisterRequest));
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<UserAuthResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok().body(authService.login(userLoginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        authService.logout(appUserDetails.getAppUser());
        return ResponseEntity.ok().body(Map.of("Message", "User logged out successfully!"));
    }

    // endregion
}
