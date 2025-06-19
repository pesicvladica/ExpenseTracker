package com.pesicvladica.expensetracker.dto;

public class UserLoginRequest {

    // region Parameters

    private final String usernameOrEmail;
    private String password;

    // endregion

    // region Initialization

    public UserLoginRequest(String usernameOrEmail, String password) {
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
