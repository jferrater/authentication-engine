package com.github.joffryferrater.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthenticationIT {

    private AuthenticationManager target;

    @BeforeEach
    void setUp() {
        target = new AuthenticationManager(new ConfigurationImpl());
    }

    @Test
    void shouldAuthenticateToLdapServer() {
        UserCredentials userCredentials = new UserCredentials("joffry", "password123");
        Optional<UserInfo> result = target.authenticateCurrentUser(userCredentials);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getUsername(), is("joffry"));
        assertThat(result.get().isAuthenticated(), is(true));
    }

    private class ConfigurationImpl implements Configuration {

    }
}
