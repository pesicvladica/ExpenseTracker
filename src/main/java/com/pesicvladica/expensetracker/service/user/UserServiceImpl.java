package com.pesicvladica.expensetracker.service.user;

import com.pesicvladica.expensetracker.dto.UserResponse;
import com.pesicvladica.expensetracker.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // region Properties

    private final AppUserRepository appUserRepository;

    // endregion

    // region Initialization

    public UserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // endregion

    // region UserService

    public UserResponse getUser(String username) {
        var user = appUserRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User could not be located")
                );
        return new UserResponse(user);
    }

    // endregion
}
