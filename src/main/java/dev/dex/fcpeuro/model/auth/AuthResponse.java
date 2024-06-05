package dev.dex.fcpeuro.model.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
