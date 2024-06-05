package dev.dex.fcpeuro.repo.auth;

import dev.dex.fcpeuro.entity.auth.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    @Query("""
            SELECT rt FROM RefreshToken rt WHERE rt.userId = :userId AND (rt.expired = false OR rt.revoked = false)
            """)
    List<RefreshToken> findAllValidTokensByUserId(Integer userId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
