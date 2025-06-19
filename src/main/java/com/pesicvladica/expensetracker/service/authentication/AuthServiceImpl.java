package com.pesicvladica.expensetracker.service.authentication;

import com.pesicvladica.expensetracker.dto.UserLoginRequest;
import com.pesicvladica.expensetracker.dto.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.UserAuthResponse;
import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.repository.AppUserRepository;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import com.pesicvladica.expensetracker.util.security.JsonWebToken;
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
    private final JsonWebToken jsonWebToken;

    // endregion

    // region Initialization

    public AuthServiceImpl(AppUserRepository appUserRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JsonWebToken jsonWebToken) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jsonWebToken = jsonWebToken;
    }

    // endregion

    // region Private Methods

    private AppUser authenticatedUserWith(String usernameOrEmail, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        return appUserDetails.getAppUser();
    }

    private String accessTokenFor(AppUser user) {
        return jsonWebToken.generateAccessTokenFor(user);
    }

    // endregion

    // region Public Methods

    @Transactional
    public UserAuthResponse register(UserRegisterRequest request) {
        var validator = new UserRegisterRequestValidator(appUserRepository);
        validator.validate(request);

        var appUser = AppUser.regularUser(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        var savedUser = appUserRepository.save(appUser);
        request.eraseCredentials();

        return new UserAuthResponse(savedUser);
    }

    public UserAuthResponse login(UserLoginRequest request) {
        var validator = new UserLoginRequestValidator(appUserRepository);
        validator.validate(request);

        var user = authenticatedUserWith(request.getUsernameOrEmail(), request.getPassword());
        request.eraseCredentials();

        var accessToken = accessTokenFor(user);

        return new UserAuthResponse(accessToken, user);
    }

    // endregion
}
