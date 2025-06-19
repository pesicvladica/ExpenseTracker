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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    // region Properties

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRegisterRequestValidator userRegisterRequestValidator;

    @Autowired
    private UserLoginRequestValidator userLoginRequestValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JsonWebToken jsonWebToken;

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
        userRegisterRequestValidator.validate(request);

        var appUser = AppUser.regularUser(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        var savedUser = appUserRepository.save(appUser);
        request.eraseCredentials();

        return new UserAuthResponse(savedUser);
    }

    public UserAuthResponse login(UserLoginRequest request) {
        userLoginRequestValidator.validate(request);

        var user = authenticatedUserWith(request.getUsernameOrEmail(), request.getPassword());
        request.eraseCredentials();

        var accessToken = accessTokenFor(user);

        return new UserAuthResponse(accessToken, user);
    }

    @Transactional
    public void logout(AppUser user) {
        user.incrementTokenVersion();
        appUserRepository.save(user);
    }

    // endregion
}
