package dev.dex.fcpeuro.model.auth;

import jakarta.validation.constraints.*;

public record AuthRequest(
        @Email
        String email,
        @Size(min = 7)
        String password,
        boolean rememberMe
) {
}
