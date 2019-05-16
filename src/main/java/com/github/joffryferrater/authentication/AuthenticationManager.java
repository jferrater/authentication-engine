package com.github.joffryferrater.authentication;

import com.github.joffryferrater.authentication.config.LdapConfig;
import com.github.joffryferrater.authentication.config.RealmConfig;
import java.util.Optional;
import org.apache.shiro.mgt.DefaultSecurityManager;

public class AuthenticationManager extends Authenticator {

    private Configuration configuration;

    public AuthenticationManager(Configuration configuration) {
        this.configuration = configuration;
    }

    public Optional<UserInfo> authenticateCurrentUser(UserCredentials userCredentials) {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        RealmConfig realmConfig = configuration.getRealmConfig();
        if(realmConfig instanceof LdapConfig) {
            LdapRealmAuthenticator ldapRealmAuthenticator = new LdapRealmAuthenticator((LdapConfig) realmConfig);
            ldapRealmAuthenticator.initializeSecurityManager(defaultSecurityManager);
            return authenticate(userCredentials);
        }
        return Optional.empty();
    }
}
