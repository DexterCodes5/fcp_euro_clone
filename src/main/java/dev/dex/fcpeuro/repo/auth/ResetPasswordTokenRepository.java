package dev.dex.fcpeuro.repo.auth;

import dev.dex.fcpeuro.entity.auth.resetpasswordtoken.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, ResetPasswordTokenId> {
    Optional<ResetPasswordToken> findByToken(String token);

    List<ResetPasswordToken> findByUserIdAndActiveTrue(Integer userId);
}
