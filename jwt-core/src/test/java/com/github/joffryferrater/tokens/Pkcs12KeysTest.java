package com.github.joffryferrater.tokens;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.security.KeyPair;
import org.junit.jupiter.api.Test;

class Pkcs12KeysTest {

    private static final String KEYSTORE = "src/test/resources/keystore.p12";

    @Test
    void shouldReturnTheKeyPair() {
        KeyPair result = Pkcs12Keys.keyPairForFile(KEYSTORE, "secret", "Test");

        assertThat(result, is(notNullValue()));
        assertThat(result.getPrivate(), is(notNullValue()));
        assertThat(result.getPublic(), is(notNullValue()));
    }
}