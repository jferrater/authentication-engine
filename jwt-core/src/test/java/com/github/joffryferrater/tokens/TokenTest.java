package com.github.joffryferrater.tokens;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.KeyPair;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TokenTest {

    @Test
    void shouldCreateASignedToken() {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        Token target = new Token(keyPair);
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject("Joffry");

        Optional<String> result = target.getSignedToken(jwtBuilder);

        assertThat(result.isPresent(), is(true));
        String subject = Jwts.parser().setSigningKey(keyPair.getPrivate()).parseClaimsJws(result.get()).getBody()
            .getSubject();
        assertThat(subject, is("Joffry"));
    }
}