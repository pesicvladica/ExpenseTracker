package com.pesicvladica.expensetracker.dto;

import com.pesicvladica.expensetracker.model.AppUser;

import java.time.LocalDateTime;

public class UserResponse {

    // region Properties

    private final Long userId;
    private final String username;
    private final String email;
    private final String role;
    private final LocalDateTime createdAt;

    // endregion

    // region Initialization

    public UserResponse(AppUser appUser) {
        this.userId = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
        this.role = appUser.getRole().toString();
        this.createdAt = appUser.getCreatedAt();
    }

    // endregion

    // region Getters

    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // endregion
}
