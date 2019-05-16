package com.github.joffryferrater.authentication;

import static org.apache.shiro.realm.jdbc.JdbcRealm.SaltStyle.NO_SALT;

import com.github.joffryferrater.authentication.config.JdbcConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcRealmAuthenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcRealmAuthenticator.class);

    private static HikariConfig hikariConfig = new HikariConfig();
    private static HikariDataSource hikariDataSource;

    private JdbcConfig jdbcConfig;

    public JdbcRealmAuthenticator(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public void initializeSecurityManager(DefaultSecurityManager defaultSecurityManager) {
        if (jdbcConfig != null) {
            LOGGER.info("Setting jdbc realm to security manager");
            setJdbcRealm(defaultSecurityManager);
        }
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    private void setJdbcRealm(DefaultSecurityManager defaultSecurityManager) {
        setHikariDatasource();
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setAuthenticationCachingEnabled(true);
        jdbcRealm.setDataSource(hikariDataSource);
        jdbcRealm.setSaltStyle(NO_SALT);
        jdbcRealm.setPermissionsLookupEnabled(true);
        jdbcRealm.setPermissionsQuery(jdbcConfig.getPermissionsQuery());
        jdbcRealm.setAuthenticationQuery(jdbcConfig.getAuthenticationQuery());
        jdbcRealm.setUserRolesQuery(jdbcConfig.getRolesQuery());
        defaultSecurityManager.setRealm(jdbcRealm);
    }

    private void setHikariDatasource() {
        hikariConfig.setJdbcUrl(jdbcConfig.getUrl());
        hikariConfig.setUsername(jdbcConfig.getUser());
        hikariConfig.setPassword(jdbcConfig.getPassword());
        hikariConfig.setDriverClassName(jdbcConfig.getDriverClassName());
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariDataSource = new HikariDataSource(hikariConfig);
    }
}
