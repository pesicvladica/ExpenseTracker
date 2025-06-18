package com.pesicvladica.expensetracker.dto;

import com.pesicvladica.expensetracker.model.AppUser;

public class UserResponse {

    // region Properties

    private final Long userId;
    private final String username;
    private final String email;

    // endregion

    // region Initialization

    public UserResponse(AppUser appUser) {
        this.userId = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
    }

    // endregion

    // region Getters

    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    // endregion
}
