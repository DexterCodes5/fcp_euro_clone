package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.model.auth.*;
import dev.dex.fcpeuro.service.*;
import jakarta.mail.*;
import jakarta.servlet.http.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) throws MessagingException {
        authService.register(registerRequest);
    }

    @PostMapping("/verify-email")
    public void verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@Valid @RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestParam @Email String email) throws MessagingException {
        authService.forgotPassword(email);
    }

    @GetMapping("/is-reset-password-token-valid")
    public void isResetPasswordTokenValid(@RequestParam String token) {
        authService.isResetPasswordTokenValid(token);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        authService.changePassword(changePasswordRequest);
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getUser());
    }
}
