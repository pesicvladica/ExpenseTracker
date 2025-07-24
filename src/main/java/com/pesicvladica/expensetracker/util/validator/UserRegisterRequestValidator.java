package com.pesicvladica.expensetracker.util.validator;

import com.pesicvladica.expensetracker.dto.user.UserRegisterRequest;
import com.pesicvladica.expensetracker.exception.CredentialsInvalidException;
import com.pesicvladica.expensetracker.exception.UserAlreadyExistsException;
import com.pesicvladica.expensetracker.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterRequestValidator implements Validator<UserRegisterRequest> {

    // region Properties

    @Autowired
    private AppUserRepository appUserRepository;

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
