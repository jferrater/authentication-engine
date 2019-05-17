package com.github.joffryferrater.tokens;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

        Optional<String> result = target.getSignedToken();

        assertThat(result.isPresent(), is(true));
    }
}