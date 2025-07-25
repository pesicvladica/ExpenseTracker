package com.pesicvladica.expensetracker.controller;

import com.pesicvladica.expensetracker.dto.user.UserLoginRequest;
import com.pesicvladica.expensetracker.dto.user.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.user.UserAuthResponse;
import com.pesicvladica.expensetracker.service.authentication.AuthService;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import com.pesicvladica.expensetracker.util.helpers.DeviceInfoHelper;
import com.pesicvladica.expensetracker.util.security.token.JWTTokenService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
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
    private final JWTTokenService JWTTokenService;

    // endregion

    // region Initialization

    public AuthController(AuthService authService,
                          JWTTokenService JWTTokenService) {
        this.authService = authService;
        this.JWTTokenService = JWTTokenService;
    }

    // endregion

    // region API Endpoints

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<UserAuthResponse> register(HttpServletRequest request,
                                                     @RequestBody UserRegisterRequest userRegisterRequest) {
        var deviceInfo = DeviceInfoHelper.getDeviceInfoFromRequest(request);
        return ResponseEntity.ok().body(new UserAuthResponse(authService.register(userRegisterRequest, deviceInfo)));
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<UserAuthResponse> login(HttpServletRequest request,
                                                  @RequestBody UserLoginRequest userLoginRequest) {
        var deviceInfo = DeviceInfoHelper.getDeviceInfoFromRequest(request);
        var user = authService.login(userLoginRequest, deviceInfo);
        var token = JWTTokenService.generateAccessTokenFor(user);
        return ResponseEntity.ok().body(new UserAuthResponse(token, user));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        authService.logout(appUserDetails.getAppUser());
        return ResponseEntity.ok().body(Map.of("Message", "User logged out successfully!"));
    }

    // endregion
}
