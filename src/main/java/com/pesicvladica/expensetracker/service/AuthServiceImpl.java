package com.pesicvladica.expensetracker.service;

import com.pesicvladica.expensetracker.dto.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.UserResponse;
import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.repository.AppUserRepository;
import com.pesicvladica.expensetracker.util.UserRegisterRequestValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    // region Properties

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    // endregion

    // region Initialization

    public AuthServiceImpl(AppUserRepository appUserRepository,
                           PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // endregion

    // region Public Methods

    @Transactional
    public UserResponse register(UserRegisterRequest request) {
        var validator = new UserRegisterRequestValidator(appUserRepository);
        validator.validate(request);

        var appUser = AppUser.regularUser(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        var savedUser = appUserRepository.save(appUser);
        request.eraseCredentials();

        return new UserResponse(savedUser);
    }

    // endregion
}
