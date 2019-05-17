package com.github.joffryferrater.tokens;

import io.jsonwebtoken.JwtBuilder;
import java.security.KeyPair;
import java.util.Optional;

public class Token {

    private KeyPair keyPair;

    public Token(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public Optional<String> getSignedToken(JwtBuilder jwtBuilder) {
        String jws = jwtBuilder.signWith(keyPair.getPrivate()).compact();
        return Optional.of(jws);
    }
}
