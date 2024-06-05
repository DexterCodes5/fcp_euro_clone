package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.repo.auth.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        accessToken = authHeader.substring(7);
        // Revoke all user tokens
        AccessToken accessTokenEntity = accessTokenRepository.findFirstByAccessTokenOrderByIdDesc(accessToken)
                .orElseThrow(() -> new BadCredentialsException("Invalid jwt token"));
        List<AccessToken> accessTokenEntities = accessTokenRepository.findAllValidTokensByUserId(accessTokenEntity.getUserId());
        accessTokenEntities.forEach(at -> {
            at.setExpired(true);
            at.setRevoked(true);
        });
        accessTokenRepository.saveAll(accessTokenEntities);
        List<RefreshToken> refreshTokenEntities = refreshTokenRepository.findAllValidTokensByUserId(accessTokenEntity.getUserId());
        refreshTokenEntities.forEach(rt -> {
            rt.setExpired(true);
            rt.setRevoked(true);
        });
        refreshTokenRepository.saveAll(refreshTokenEntities);
        SecurityContextHolder.clearContext();
    }
}
