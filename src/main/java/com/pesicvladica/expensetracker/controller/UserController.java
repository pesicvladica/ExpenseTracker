package com.pesicvladica.expensetracker.controller;

import com.pesicvladica.expensetracker.dto.user.UserResponse;
import com.pesicvladica.expensetracker.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // region Properties

    private final UserService userService;

    // endregion

    // region Initialization

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // endregion

    // region API Endpoints

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getCurrent(Authentication authentication) {
        var username = authentication.getName();
        return ResponseEntity.ok().body(new UserResponse(userService.getUser(username)));
    }

    // endregion

}
