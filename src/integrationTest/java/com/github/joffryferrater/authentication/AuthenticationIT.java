package com.github.joffryferrater.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.joffryferrater.authentication.config.LdapConfig;
import java.util.Optional;
import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(Lifecycle.PER_CLASS)
class AuthenticationIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationIT.class);

    private AuthenticationManager target;

    @AfterEach
    void tearDown() {
        SecurityUtils.setSecurityManager(null);
    }

    @Test
    void shouldAuthenticateToLdapServer() {
        Configuration configuration = () -> {
            LdapConfig ldapConfig = new LdapConfig();
            ldapConfig.setUrl("ldap://localhost:389");
            ldapConfig.setAdminDn("");
            ldapConfig.setPassword("");
            ldapConfig.setOrganizationUnit("ou=users");
            return ldapConfig;
        };
        target = new AuthenticationManager(configuration);
        UserCredentials userCredentials = new UserCredentials("joffry", "password");
        Optional<UserInfo> result = target.authenticateCurrentUser(userCredentials);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getUsername(), is("joffry"));
        assertThat(result.get().isAuthenticated(), is(true));
    }
}
