package com.example.Ecommerce_Web.Security;


import com.example.Ecommerce_Web.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImp implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImp(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImp build(User user) {

        List<GrantedAuthority> authority = user.getRole().stream()
                .map((role -> new SimpleGrantedAuthority(role.getName())))
                .collect(Collectors.toUnmodifiableList());

        return new UserDetailsImp(user.getUserEmail(), user.getUserPassword(), authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // or add your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // or add your logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // or add your logic
    }

    @Override
    public boolean isEnabled() {
        return true;  // or add your logic
    }

}