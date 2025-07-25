package com.pesicvladica.expensetracker.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesicvladica.expensetracker.model.AppUser;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UserAuthResponse {

    // region Properties

    private final String accessToken;
    private final Long userId;
    private final String username;
    private final String email;

    // endregion

    // region Initialization

    public UserAuthResponse(AppUser appUser) {
        this.accessToken = null;
        this.userId = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
    }

    public UserAuthResponse(String accessToken, AppUser appUser) {
        this.accessToken = accessToken;
        this.userId = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
    }

    // endregion

    // region Getters

    public String getAccessToken() { return accessToken; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    // endregion
}
