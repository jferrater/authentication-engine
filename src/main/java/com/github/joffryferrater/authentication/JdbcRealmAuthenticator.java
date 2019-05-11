package com.github.joffryferrater.authentication;

import com.github.joffryferrater.authentication.config.JdbcConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcRealmAuthenticator extends Authenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcRealmAuthenticator.class);
    
    private JdbcConfig jdbcConfig;

    public JdbcRealmAuthenticator(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public void initializeSecurityManager() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        if (jdbcConfig != null) {
            LOGGER.info("Setting jdbc realm to security manager");
            setJdbcRealm(defaultSecurityManager);
        }
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    private void setJdbcRealm(DefaultSecurityManager defaultSecurityManager) {
    }

}
