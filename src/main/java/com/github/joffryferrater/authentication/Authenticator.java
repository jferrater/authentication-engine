package com.github.joffryferrater.authentication;

import java.util.Optional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Authenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Authenticator.class);

    public Optional<UserInfo> authenticate(UserCredentials userCredentials) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            final String username = userCredentials.getUsername();
            final String password = userCredentials.getPassword();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                currentUser.login(usernamePasswordToken);
                LOGGER.info("The user {} is authenticated", username);
                return Optional.of(new UserInfo(username, true));
            } catch (UnknownAccountException e) {
                LOGGER.error("The user with username {} does not exist!", username, e.getMessage());
            } catch (AuthenticationException e) {
                LOGGER.error("Invalid username or password: {}", e.getMessage());
            } finally {
                ThreadContext.unbindSubject();
            }
        }
        return Optional.empty();
    }
}
