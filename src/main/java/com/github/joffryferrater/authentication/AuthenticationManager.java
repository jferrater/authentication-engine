package com.github.joffryferrater.authentication;

import com.github.joffryferrater.authentication.config.SecurityConfig;

public class AuthenticationManager {

    private SecurityConfig securityConfig;

    public AuthenticationManager(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    public void initializeSecurityManager() {
    }
}
