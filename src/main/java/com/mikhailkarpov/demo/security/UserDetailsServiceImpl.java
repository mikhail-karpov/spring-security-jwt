package com.mikhailkarpov.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("userDetailsService")
public class UserDetailsServiceImpl extends InMemoryUserDetailsManager {

    private PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void createUsers() {
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder.encode("adminpass"))
                .roles("admin").build();
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder.encode("userpass"))
                .roles("user").build();

        this.createUser(admin);
        this.createUser(user);
    }
}