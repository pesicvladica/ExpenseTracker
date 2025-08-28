package com.pesicvladica.expensetracker.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserRegisterRequest {

    // region Parameters

    private final String username;
    private final String email;
    private String password;

    // endregion

    // region Initialization

    @JsonCreator
    public UserRegisterRequest(
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // endregion

    // region Getters

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // endregion

    // region Public Methods

    public void eraseCredentials() { password = null; }

    // endregion
}
