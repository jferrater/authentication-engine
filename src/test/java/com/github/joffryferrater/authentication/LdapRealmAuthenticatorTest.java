package com.github.joffryferrater.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.joffryferrater.authentication.config.LdapConfig;
import java.util.Collection;
import java.util.Optional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class LdapRealmAuthenticatorTest {

    private LdapConfig ldapConfig;

    @BeforeAll
    void setUp() {
        ldapConfig = new LdapConfig();
        ldapConfig.setAdminDn("uid=admin,dc=com");
        ldapConfig.setUserDnTemplate("uid={0},ou=Users,dc=myOrg,dc=com");
        ldapConfig.setPassword("password");
        ldapConfig.setUrl("ldap://localhost:389");
    }

    @AfterAll
    void tearDown() {
        ThreadContext.unbindSecurityManager();
    }

    @Test
    void shouldUseLdapRealm() {
        LdapRealmAuthenticator target = new LdapRealmAuthenticator(ldapConfig);
        target.initializeSecurityManager();

        DefaultSecurityManager defaultSecurityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
        Collection<Realm> realms = defaultSecurityManager.getRealms();
        Optional<Realm> result = realms.stream().filter(realm -> realm instanceof DefaultLdapRealm).findFirst();

        assertThat(result.isPresent(), is(true));
    }
}