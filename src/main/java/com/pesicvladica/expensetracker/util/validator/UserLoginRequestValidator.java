package com.pesicvladica.expensetracker.util.validator;

import com.pesicvladica.expensetracker.dto.UserLoginRequest;
import com.pesicvladica.expensetracker.exception.CredentialsInvalidException;
import com.pesicvladica.expensetracker.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLoginRequestValidator  implements Validator<UserLoginRequest> {

    // region Properties

    @Autowired
    private AppUserRepository appUserRepository;

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
