package com.pesicvladica.expensetracker.util;

import com.pesicvladica.expensetracker.dto.UserRegisterRequest;
import com.pesicvladica.expensetracker.exception.CredentialsInvalidException;
import com.pesicvladica.expensetracker.exception.UserAlreadyExistsException;
import com.pesicvladica.expensetracker.repository.AppUserRepository;

public class UserRegisterRequestValidator implements Validator<UserRegisterRequest> {

    // region Properties

    private final AppUserRepository appUserRepository;

    // endregion

    // region Initialization

    public UserRegisterRequestValidator(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // endregion

    // region Validator

    @Override
    public void validate(UserRegisterRequest obj) {
        if (!obj.getUsername().isBlank() && appUserRepository.existsByUsername(obj.getUsername())) {
            throw new UserAlreadyExistsException("Username " + obj.getUsername() + " already taken!");
        }
        if (!obj.getEmail().isBlank() && appUserRepository.existsByEmail(obj.getEmail())) {
            throw new UserAlreadyExistsException("Email " + obj.getEmail() + " already taken!");
        }
        if (obj.getPassword().length() < 8) {
            throw new CredentialsInvalidException("Password too short!");
        }
    }

    // endregion
}
