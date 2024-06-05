package dev.dex.fcpeuro.repo.auth;

import dev.dex.fcpeuro.entity.auth.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RefreshTokenRepositoryTest {
    @Autowired
    private RefreshTokenRepository underTest;

    @Test
    void canFindAllValidTokensByUserId() {
        // given
        int userId = 1;
        List<RefreshToken> refreshTokens = new ArrayList<>(List.of(
                new RefreshToken(1, userId, "refresh-token", true, true, true),
                new RefreshToken(2, userId, "refresh-token", false, false, true)));
        underTest.saveAll(refreshTokens);

        // when
        List<RefreshToken> res = underTest.findAllValidTokensByUserId(userId);

        // then
        assertThat(res.get(0))
                .usingRecursiveComparison()
                .isEqualTo(refreshTokens.get(1));
    }
}