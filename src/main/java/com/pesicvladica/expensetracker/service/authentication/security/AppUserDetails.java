package com.pesicvladica.expensetracker.service.authentication.security;

import com.pesicvladica.expensetracker.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {

    // region Properties

    private final AppUser appUser;

    // endregion

    // region Initialization

    public AppUserDetails(AppUser appUser) {
        this.appUser = appUser;
    }

    // endregion

    // region Getters

    public AppUser getAppUser() { return appUser; }

    // endregion

    // region UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(new SimpleGrantedAuthority("ROLE_" + appUser.getRole())); }

    @Override
    public String getPassword() { return appUser.getPassword(); }

    @Override
    public String getUsername() { return appUser.getUsername() != null ? appUser.getUsername() : appUser.getEmail(); }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    // endregion
}
