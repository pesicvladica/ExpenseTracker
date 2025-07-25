package com.pesicvladica.expensetracker.service.user;

import com.pesicvladica.expensetracker.model.AppUser;
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

    @Override
    public AppUser getUser(String username) {
        return appUserRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User could not be located")
                );
    }

    // endregion
}
