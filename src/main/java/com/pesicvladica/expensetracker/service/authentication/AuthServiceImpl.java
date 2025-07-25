package com.pesicvladica.expensetracker.service.authentication;

import com.pesicvladica.expensetracker.dto.DeviceInfo;
import com.pesicvladica.expensetracker.dto.user.UserLoginRequest;
import com.pesicvladica.expensetracker.dto.user.UserRegisterRequest;
import com.pesicvladica.expensetracker.exception.BlockedUserException;
import com.pesicvladica.expensetracker.exception.CredentialsInvalidException;
import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.repository.AppUserRepository;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
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
    private final UserRegisterRequestValidator userRegisterRequestValidator;
    private final UserLoginRequestValidator userLoginRequestValidator;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final LoginAttemptService loginAttemptService;

    // endregion

    // region Initialization

    public AuthServiceImpl(AppUserRepository appUserRepository,
                           UserRegisterRequestValidator userRegisterRequestValidator,
                           UserLoginRequestValidator userLoginRequestValidator,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           LoginAttemptService loginAttemptService) {
        this.appUserRepository = appUserRepository;
        this.userRegisterRequestValidator = userRegisterRequestValidator;
        this.userLoginRequestValidator = userLoginRequestValidator;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.loginAttemptService = loginAttemptService;
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

    @Override
    @Transactional
    public AppUser register(UserRegisterRequest request, DeviceInfo deviceInfo) {
        if (loginAttemptService.isBlocked(null, deviceInfo) || loginAttemptService.isBlocked("", deviceInfo)) {
            throw new BlockedUserException("Registration not allowed from this IP address.");
        }

        userRegisterRequestValidator.validate(request);

        var appUser = AppUser.regularUser(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        var savedUser = appUserRepository.save(appUser);
        request.eraseCredentials();

        return savedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser login(UserLoginRequest request, DeviceInfo deviceInfo) {
        if (loginAttemptService.isBlocked(request.getUsernameOrEmail(), deviceInfo)) {
            throw new BlockedUserException("Login failed (account or IP blocked).");
        }

        try {
            userLoginRequestValidator.validate(request);

            var user = authenticatedUserWith(request.getUsernameOrEmail(), request.getPassword());
            request.eraseCredentials();

            loginAttemptService.loginSucceeded(request.getUsernameOrEmail());
            return user;
        } catch(RuntimeException ex) {
            loginAttemptService.loginFailed(request.getUsernameOrEmail(), deviceInfo);
            throw new CredentialsInvalidException("Could not authenticate!");
        }
    }

    @Override
    @Transactional
    public void logout(AppUser user) {
        user.incrementTokenVersion();
        appUserRepository.save(user);
    }

    // endregion
}
