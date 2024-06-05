package dev.dex.fcpeuro.model.auth;

import jakarta.validation.constraints.*;

public record ChangePasswordRequest(
        @Size(min = 64)
        String token,
        @Size(min = 7)
        String password
) {
}
