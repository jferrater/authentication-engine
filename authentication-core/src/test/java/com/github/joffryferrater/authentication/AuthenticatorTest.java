package com.github.joffryferrater.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class AuthenticatorTest {

    private static final String PRINCIPAL = "joffry";
    private static final String PASSWORD = "password123";
    private static DefaultSecurityManager securityManager;
    private Authenticator target;

    @BeforeAll
    void setUp() {
        securityManager = new DefaultSecurityManager();
        securityManager.setRealm(new TestRealm());
        SecurityUtils.setSecurityManager(securityManager);
        target = new Authenticator();
    }

    @AfterEach
    void clearCurrentUser() {
        target.removeCurrentUserFromContext();
    }

    @AfterAll
    void tearDown() {
        securityManager.destroy();
    }

    @Test
    void shouldAuthenticateUser() {
        UserCredentials userCredentials = new UserCredentials("joffry", "password123");
        Optional<UserInfo> result = target.authenticate(userCredentials);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    void shouldNotAuthenticateUnknownUser() {
        UserCredentials userCredentials = new UserCredentials("unknown user", "password123");
        Optional<UserInfo> result = target.authenticate(userCredentials);

        assertThat(result.isPresent(), is(false));
    }

    private class TestRealm extends AuthorizingRealm {

        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            return null;
        }

        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
            Object principal = token.getPrincipal();
            if (PRINCIPAL.equalsIgnoreCase(principal.toString())) {
                return new SimpleAuthenticationInfo(PRINCIPAL, PASSWORD, "TestRealm");
            }
            throw new AuthenticationException("Username or password is invalid");
        }
    }
}