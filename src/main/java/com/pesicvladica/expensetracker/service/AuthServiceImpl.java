package com.pesicvladica.expensetracker.service;

import com.pesicvladica.expensetracker.dto.UserLoginRequest;
import com.pesicvladica.expensetracker.dto.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.UserResponse;
import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.repository.AppUserRepository;
import com.pesicvladica.expensetracker.service.secure.AppUserDetails;
import com.pesicvladica.expensetracker.util.validator.UserLoginRequestValidator;
import com.pesicvladica.expensetracker.util.validator.UserRegisterRequestValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    // region Properties

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // endregion

    // region Initialization

    public AuthServiceImpl(AppUserRepository appUserRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // endregion

    // region Private Methods

    private AppUser authenticatedUserWith(String usernameOrEmail, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        return appUserDetails.getAppUser();
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

    public UserResponse login(UserLoginRequest request) {
        var validator = new UserLoginRequestValidator(appUserRepository);
        validator.validate(request);

        var user = authenticatedUserWith(request.getUsernameOrEmail(), request.getPassword());
        request.eraseCredentials();

        return new UserResponse(user);
    }

    // endregion
}
