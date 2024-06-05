package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.auth.*;
import org.junit.jupiter.api.*;
import org.springframework.test.util.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;


class JwtServiceTest {
    private JwtService underTest;

    @BeforeEach
    void setUp() {
        underTest = new JwtService();
        ReflectionTestUtils.setField(underTest, "secretKey", "vanUyiqmjYb8Q1EPZqDI0aucIGc84/cA8LWcALbngHwseTBYlnNKSfyVGEqeFHTX");
        ReflectionTestUtils.setField(underTest, "accessExpiration", 18000000);
        ReflectionTestUtils.setField(underTest, "refreshExpiration", 28800000);
        ReflectionTestUtils.setField(underTest, "refreshRememberMeExpiration", 2592000000l);
    }
    @Test
    void canIsTokenValid() {
        // given
        User user = new User(1, "email", "username", "password", Role.USER, true,
                null);
        String accessToken = underTest.generateAccessToken(user);

        // when
        boolean res = underTest.isTokenValid(accessToken, user);

        // then
        assertThat(res).isTrue();
    }

    @Test
    void canExtractEmail() {
        // given
        User user = new User(1, "email", "username", "password", Role.USER, true,
                null);
        String accessToken = underTest.generateAccessToken(user);

        // when
        String res = underTest.extractEmail(accessToken);

        // then
        assertThat(res).isEqualTo(user.getEmail());
    }

    @Test
    void canExtractClaim() {
        // given
        String username = "username";
        User user = new User(1, "email", username, "password", Role.USER, true,
                null);
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        String accessToken = underTest.generateAccessToken(claims, user);

        // when
        String res = underTest.extractClaim(accessToken, claims1 -> claims1.get(username, String.class));

        // then
        assertThat(res).isEqualTo(username);
    }

}