package com.github.joffryferrater.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.joffryferrater.authentication.config.JdbcConfig;
import java.util.Optional;
import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class JdbcAuthenticationIT {

    private AuthenticationManager target;

    @BeforeAll
    void setUp() {
        Configuration configuration = () -> {
            JdbcConfig jdbcConfig = new JdbcConfig();
            jdbcConfig.setDriverClassName("org.postgresql.Driver");
            jdbcConfig.setUser("postgres");
            jdbcConfig.setPassword("password");
            jdbcConfig.setUrl("jdbc:postgresql://localhost:5432/users");
            jdbcConfig.setAuthenticationQuery("select password  from users where username = ?");
            return jdbcConfig;
        };
        target = new AuthenticationManager(configuration);
    }

    @AfterEach
    void resetUser() {
        target.removeCurrentUserFromContext();
    }

    @AfterAll
    void tearDown() {
        SecurityUtils.setSecurityManager(null);
    }

    @Test
    void shouldAuthenticateFromJdbcRealmUsingPostgres() {
        String username = "joffry";
        String password = "password123";
        UserCredentials userCredentials = new UserCredentials(username, password);

        Optional<UserInfo> result = target.authenticateCurrentUser(userCredentials);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getUsername(), is(username));
        assertThat(result.get().isAuthenticated(), is(true));
    }

    @Test
    void shouldNotBeAuthenticatedForInvalidPassword() {
        String username = "joffry";
        String password = "password1234";
        UserCredentials userCredentials = new UserCredentials(username, password);

        Optional<UserInfo> result = target.authenticateCurrentUser(userCredentials);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void shouldNotBeAuthenticatedForUnknownUser() {
        String username = "leo";
        String password = "password123";
        UserCredentials userCredentials = new UserCredentials(username, password);

        Optional<UserInfo> result = target.authenticateCurrentUser(userCredentials);

        assertThat(result.isPresent(), is(false));
    }
}
