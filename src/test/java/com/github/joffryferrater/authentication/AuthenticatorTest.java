package com.github.joffryferrater.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthenticatorTest {

    private Authenticator target;

    @BeforeEach
    void setUp() {
        target = new Authenticator();
    }

    @Test
    void shouldAuthenticateUser() {
        UserCredentials userCredentials = new UserCredentials("joffry", "password123");
        Optional<UserInfo> result = target.authenticate(userCredentials);

        assertThat(result.isPresent(), is(true));
    }
}