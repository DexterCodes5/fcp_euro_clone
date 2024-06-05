package dev.dex.fcpeuro.repo.auth;

import dev.dex.fcpeuro.entity.auth.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AccessTokenRepositoryTest {
    @Autowired
    private AccessTokenRepository underTest;

    @Test
    void canFindAllValidTokensByUserId() {
        // given
        int userId = 1;
        AccessToken accessToken = new AccessToken(1, userId, "access-token", true, true);
        AccessToken accessToken2 = new AccessToken(2, userId, "access-token", false, false);
        underTest.saveAll(List.of(accessToken, accessToken2));

        // when
        List<AccessToken> res = underTest.findAllValidTokensByUserId(userId);

        // then
        assertThat(res.get(0))
                .usingRecursiveComparison()
                .isEqualTo(accessToken2);
    }
}