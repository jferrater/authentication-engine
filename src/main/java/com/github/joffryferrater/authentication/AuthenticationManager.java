package com.github.joffryferrater.authentication;

import com.github.joffryferrater.authentication.config.LdapConfig;
import com.github.joffryferrater.authentication.config.RealmConfig;
import java.util.Optional;

public class AuthenticationManager {

    private Configuration configuration;

    public AuthenticationManager(Configuration configuration) {
        this.configuration = configuration;
    }

    public Optional<UserInfo> authenticateCurrentUser(UserCredentials userCredentials) {
        RealmConfig realmConfig = configuration.getRealmConfig();
        if(realmConfig instanceof LdapConfig) {
            LdapRealmAuthenticator ldapRealmAuthenticator = new LdapRealmAuthenticator((LdapConfig) realmConfig);
            ldapRealmAuthenticator.initializeSecurityManager();
            return ldapRealmAuthenticator.authenticate(userCredentials);
        }
        return Optional.empty();
    }
}
