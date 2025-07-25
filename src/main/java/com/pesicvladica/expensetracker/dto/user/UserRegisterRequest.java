package com.pesicvladica.expensetracker.dto.user;

public final class UserRegisterRequest {

    // region Parameters

    private final String username;
    private final String email;
    private String password;

    // endregion

    // region Initialization

    public UserRegisterRequest(String username, String email, String password) {
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
