package com.github.joffryferrater.authentication;

import java.util.Optional;

public class AuthenticationManager {

    private Configuration configuration;

    public AuthenticationManager(Configuration configuration) {
        this.configuration = configuration;
    }

    public Optional<UserInfo> authenticateCurrentUser(UserCredentials userCredentials) {
        return Optional.empty();
    }
}
