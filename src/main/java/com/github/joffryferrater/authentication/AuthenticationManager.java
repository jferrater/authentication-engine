package com.github.joffryferrater.authentication;

import com.github.joffryferrater.authentication.config.LdapConfig;
import com.github.joffryferrater.authentication.config.SecurityConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationManager.class);
    private static final String UID_TEMPLATE = "uid={0}";

    private SecurityConfig securityConfig;

    public AuthenticationManager(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    public void initializeSecurityManager() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        if (securityConfig.getLdapConfig() != null) {
            LOGGER.info("Setting ldap realm to security manager");
            setLdapRealm(defaultSecurityManager);
        }
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    private void setLdapRealm(DefaultSecurityManager securityManager) {
        LdapConfig ldapConfig = securityConfig.getLdapConfig();
        DefaultLdapRealm ldapRealm = new DefaultLdapRealm();
        ldapRealm.setUserDnTemplate(UID_TEMPLATE + ldapConfig.getOrganizationUnit());
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
