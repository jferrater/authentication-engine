package com.github.joffryferrater.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.joffryferrater.authentication.config.JdbcConfig;
import java.util.Collection;
import java.util.Optional;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class JdbcRealmAuthenticatorTest {

    private JdbcConfig jdbcConfig;

    @BeforeAll
    void setUp() {
        jdbcConfig = new JdbcConfig();
        jdbcConfig.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        jdbcConfig.setUrl("jdbc:hsqldb:mem:test");
        jdbcConfig.setUser("user");
        jdbcConfig.setPassword("password");
    }

    @AfterAll
    void tearDown() {
        ThreadContext.unbindSecurityManager();
    }

    @Test
    void shouldUseJdbcRealm() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        JdbcRealmAuthenticator target = new JdbcRealmAuthenticator(jdbcConfig);
        target.initializeSecurityManager(defaultSecurityManager);

        Collection<Realm> realms = defaultSecurityManager.getRealms();
        Optional<Realm> result = realms.stream().filter(realm -> realm instanceof JdbcRealm).findFirst();

        assertThat(result.isPresent(), is(true));
    }
}