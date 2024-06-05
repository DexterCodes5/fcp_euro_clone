package dev.dex.fcpeuro.repo.auth;

import dev.dex.fcpeuro.entity.auth.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {
    Optional<AccessToken> findFirstByAccessTokenOrderByIdDesc(String accessToken);

    @Query("""
            SELECT at FROM AccessToken at WHERE at.userId = :userId AND (at.expired = false or at.revoked = false)
            """)
    List<AccessToken> findAllValidTokensByUserId(Integer userId);
}
