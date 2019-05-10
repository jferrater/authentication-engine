package com.github.joffryferrater.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.joffryferrater.authentication.config.SecurityConfig;
import java.util.Collection;
import java.util.Optional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class AuthenticationManagerTest {

    @Test
    void shouldUseLdapRealm() {
        SecurityConfig securityConfig = new SecurityConfig();
        AuthenticationManager authenticationManager = new AuthenticationManager(securityConfig);
        authenticationManager.initializeSecurityManager();

        DefaultSecurityManager defaultSecurityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
        Collection<Realm> realms = defaultSecurityManager.getRealms();
        Optional<Realm> result = realms.stream().filter(realm -> realm instanceof DefaultLdapRealm).findFirst();

        assertThat(result.isPresent(), is(true));
    }
}