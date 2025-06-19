package com.pesicvladica.expensetracker.service.authentication.security;

import com.pesicvladica.expensetracker.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

    // region Properties

    private final AppUserRepository appUserRepository;

    // endregion

    // region Initialization

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // endregion

    // region UserDetailsService

    @Override
    public com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        var appUser = appUserRepository
                .findByUsername(usernameOrEmail)
                .orElseGet(() ->
                        appUserRepository
                                .findByEmail(usernameOrEmail)
                                .orElseThrow(() ->
                                        new UsernameNotFoundException("Username or email not found!")
                                )
                );
        return new com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails(appUser);
    }

    // endregion
}
