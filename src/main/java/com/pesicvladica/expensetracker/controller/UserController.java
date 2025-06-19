package com.pesicvladica.expensetracker.controller;

import com.pesicvladica.expensetracker.dto.UserResponse;
import com.pesicvladica.expensetracker.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    // endregion

    // region API Endpoints

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getCurrent(Authentication authentication) {
        var username = authentication.getName();
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    // endregion

}
