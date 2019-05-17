package com.github.joffryferrater.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.joffryferrater.authentication.config.LdapConfig;
import java.util.Optional;
import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class LdapAuthenticationIT {

    private static final String LDAP_URL = "ldap://127.0.0.1:10389";
    private static final String LDAP_ADMIN_DN = "uid=admin,ou=system";
    private static final String PASSWORD = "secret";
    private static final String USER_DN_TEMPLATE = "uid={0},ou=Users,dc=myorg,dc=com";
    private AuthenticationManager target;

    @BeforeAll
    void setUp() {
        Configuration configuration = () -> {
            LdapConfig ldapConfig = new LdapConfig();
            ldapConfig.setUrl(LDAP_URL);
            ldapConfig.setAdminDn(LDAP_ADMIN_DN);
            ldapConfig.setPassword(PASSWORD);
            ldapConfig.setUserDnTemplate(USER_DN_TEMPLATE);
            return ldapConfig;
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
    void shouldAuthenticateToLdapServer() {
        String username = "joffry";
        String password = "password12345";
        UserCredentials userCredentials = new UserCredentials(username, password);

        Optional<UserInfo> result = target.authenticateCurrentUser(userCredentials);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getUsername(), is(username));
        assertThat(result.get().isAuthenticated(), is(true));
    }

    @Test
    void shouldNotAuthenticateInvalidUser() {
        String username = "joffry34332";
        String password = "password12345";
        UserCredentials userCredentials = new UserCredentials(username, password);

        Optional<UserInfo> result = target.authenticateCurrentUser(userCredentials);

        assertThat(result.isPresent(), is(false));
    }
}
