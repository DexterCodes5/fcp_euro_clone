package dev.dex.fcpeuro.model.auth;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @Email
        String email,
        @Pattern(regexp = "^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$",
                message = "Username should be between 5 and 20 characters long")
        String username,

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}$",
                message = "Password must be minimum 7 characters and should have at least 1 letter and at least 1 number")
        String password
) {
}
