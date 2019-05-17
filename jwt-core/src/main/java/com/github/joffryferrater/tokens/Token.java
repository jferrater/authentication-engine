package com.github.joffryferrater.tokens;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import java.security.KeyPair;
import java.util.Optional;

public class Token {

    private KeyPair keyPair;

    public Token(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public Optional<String> getSignedToken() {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jws = jwtBuilder.setSubject("Joffry").signWith(keyPair.getPrivate()).compact();
        return Optional.of(jws);
    }
}
