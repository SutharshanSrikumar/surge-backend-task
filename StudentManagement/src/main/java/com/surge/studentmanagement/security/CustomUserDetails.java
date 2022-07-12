package com.surge.studentmanagement.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.surge.studentmanagement.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Integer id;

    private final String email;

    private final Boolean profileCompleted;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Integer id, String email, String password, Boolean profileCompleted,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profileCompleted = profileCompleted;
        this.authorities = authorities;
    }

    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getUserRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getProfileCompleted(),
                authorities);
    }

    public Integer getId() {
        return id;
    }

    public Boolean getProfileCompleted() {
        return profileCompleted;
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
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
