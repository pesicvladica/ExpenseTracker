package com.pesicvladica.expensetracker.util.validator;

import com.pesicvladica.expensetracker.dto.UserLoginRequest;
import com.pesicvladica.expensetracker.exception.CredentialsInvalidException;
import com.pesicvladica.expensetracker.exception.UserAlreadyExistsException;
import com.pesicvladica.expensetracker.repository.AppUserRepository;

public class UserLoginRequestValidator  implements Validator<UserLoginRequest> {

    // region Properties

    private final AppUserRepository appUserRepository;

    // endregion

    // region Initialization

    public UserLoginRequestValidator(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // endregion

    // region Validator

    @Override
    public void validate(UserLoginRequest obj) {
        if (obj.getUsernameOrEmail().isBlank() || obj.getPassword().isBlank()) {
            throw new CredentialsInvalidException("Username and password cant be blank!");
        }
        if (!appUserRepository.existsByUsername(obj.getUsernameOrEmail()) && !appUserRepository.existsByEmail(obj.getUsernameOrEmail())) {
            throw new CredentialsInvalidException("Username or password invalid!");
        }
        if (obj.getPassword().length() < 8) {
            throw new CredentialsInvalidException("Username or password invalid!");
        }
    }

    // endregion
}
