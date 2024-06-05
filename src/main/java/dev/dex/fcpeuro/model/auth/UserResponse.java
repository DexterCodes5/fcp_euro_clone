package dev.dex.fcpeuro.model.auth;

import dev.dex.fcpeuro.entity.auth.*;

public record UserResponse(
        Integer id,
        String email,
        String username,
        Role role
) {
}
