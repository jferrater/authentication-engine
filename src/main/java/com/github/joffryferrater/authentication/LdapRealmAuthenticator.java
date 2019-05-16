package com.github.joffryferrater.authentication;

import com.github.joffryferrater.authentication.config.LdapConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LdapRealmAuthenticator extends Authenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapRealmAuthenticator.class);

    private LdapConfig ldapConfig;

    public LdapRealmAuthenticator(LdapConfig ldapConfig) {
        this.ldapConfig = ldapConfig;
    }

    public void initializeSecurityManager() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        if (ldapConfig != null) {
            LOGGER.info("Setting ldap realm to security manager");
            setLdapRealm(defaultSecurityManager);
        }
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    private void setLdapRealm(DefaultSecurityManager securityManager) {
        DefaultLdapRealm ldapRealm = new DefaultLdapRealm();
        ldapRealm.setAuthenticationCachingEnabled(true);
        String userDnTemplate = ldapConfig.getUserDnTemplate();
        LOGGER.debug("User DN template {}", userDnTemplate);
        ldapRealm.setUserDnTemplate(userDnTemplate);
        JndiLdapContextFactory jndiLdapContextFactory = createLdapContext(ldapConfig, ldapRealm);
        ldapRealm.setContextFactory(jndiLdapContextFactory);
        securityManager.setRealm(ldapRealm);
    }

    private JndiLdapContextFactory createLdapContext(LdapConfig ldapConfig, DefaultLdapRealm ldapRealm) {
        JndiLdapContextFactory jndiLdapContextFactory = (JndiLdapContextFactory) ldapRealm.getContextFactory();
        jndiLdapContextFactory.setSystemUsername(ldapConfig.getAdminDn());
        jndiLdapContextFactory.setSystemPassword(ldapConfig.getPassword());
        jndiLdapContextFactory.setUrl(ldapConfig.getUrl());
        return jndiLdapContextFactory;
    }
}
