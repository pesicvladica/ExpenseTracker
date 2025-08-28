package com.pesicvladica.expensetracker.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserLoginRequest {

    // region Parameters

    private final String usernameOrEmail;
    private String password;

    // endregion

    // region Initialization

    @JsonCreator
    public UserLoginRequest(@JsonProperty("username") String usernameOrEmail,
                            @JsonProperty("password") String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    // endregion

    // region Getters

    public String getUsernameOrEmail() { return usernameOrEmail; }
    public String getPassword() { return password; }

    // endregion

    // region Public Methods

    public void eraseCredentials() { password = null; }

    // endregion
}
