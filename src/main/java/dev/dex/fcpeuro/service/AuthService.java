package dev.dex.fcpeuro.service;

import com.fasterxml.jackson.databind.*;
import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.entity.auth.resetpasswordtoken.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.model.auth.*;
import dev.dex.fcpeuro.repo.auth.*;
import dev.dex.fcpeuro.util.*;
import jakarta.mail.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    public void register(RegisterRequest registerRequest) throws MessagingException {
        if (userRepository.selectExistsEmail(registerRequest.email())) {
            throw new BadRequestException("Email " + registerRequest.email() + " is already taken");
        }
        if (userRepository.selectExistsUsername(registerRequest.username())) {
            throw new BadRequestException("Username " + registerRequest.username() + " is already taken");
        }
        User user = User.builder()
                .email(registerRequest.email())
                ._username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(Role.USER)
                .active(false)
                .emailVerificationToken(StringUtil.generateRandomString(64))
                .build();
        userRepository.save(user);
        mailService.sendRegisterEmail(user);
    }

    public void verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid email verification token"));
        user.setActive(true);
        user.setEmailVerificationToken(null);
        userRepository.save(user);
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );
        User user = userRepository.findByEmail(authRequest.email()).get();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user, authRequest.rememberMe());
        revokeAllUserTokens(user);
        saveUserTokens(user, accessToken, refreshToken, authRequest.rememberMe());
        return new AuthResponse(accessToken, refreshToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(refreshToken);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new BadRequestException("Email from token is invalid."));
            RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken)
                    .orElse(null);
            boolean isTokenValid = refreshTokenEntity != null && refreshTokenEntity.getExpired() == false
                    && refreshTokenEntity.getRevoked() == false;
            if (jwtService.isTokenValid(refreshToken, user) && isTokenValid) {
                String accessToken = jwtService.generateAccessToken(user);
                AuthResponse authResponse;
                if (refreshTokenEntity.getRememberMe()) {
                    String newRefreshToken = jwtService.generateRefreshToken(user, true);
                    revokeAllUserTokens(user);
                    saveUserTokens(user, accessToken, newRefreshToken, true);
                    authResponse = new AuthResponse(accessToken, newRefreshToken);
                } else {
                    revokeAllUserAccessTokens(user);
                    saveAccessToken(user, accessToken);
                    authResponse = new AuthResponse(accessToken, refreshToken);
                }
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void saveAccessToken(User user, String accessToken) {
        AccessToken accessTokenEntity = AccessToken.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .expired(false)
                .revoked(false)
                .build();
        accessTokenRepository.save(accessTokenEntity);
    }

    private void saveRefreshToken(User user, String refreshToken, boolean rememberMe) {
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .expired(false)
                .revoked(false)
                .rememberMe(rememberMe)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    private void saveUserTokens(User user, String accessToken, String refreshToken, boolean rememberMe) {
        saveAccessToken(user, accessToken);
        saveRefreshToken(user, refreshToken, rememberMe);
    }

    private void revokeAllUserTokens(User user) {
        revokeAllUserAccessTokens(user);
        revokeAllUserRefreshTokens(user);
    }

    private void revokeAllUserAccessTokens(User user) {
        List<AccessToken> validAccessTokens = accessTokenRepository.findAllValidTokensByUserId(user.getId());
        validAccessTokens.forEach(accessToken -> {
            accessToken.setExpired(true);
            accessToken.setRevoked(true);
        });
        accessTokenRepository.saveAll(validAccessTokens);
    }

    private void revokeAllUserRefreshTokens(User user) {
        List<RefreshToken> validRefreshTokens = refreshTokenRepository.findAllValidTokensByUserId(user.getId());
        validRefreshTokens.forEach(refreshToken -> {
            refreshToken.setExpired(true);
            refreshToken.setRevoked(true);
        });
        refreshTokenRepository.saveAll(validRefreshTokens);
    }

    public void forgotPassword(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User email " + email + " doesn't exist"));
        if (user.getActive() == false) {
            throw new BadRequestException("User hasn't validated his email address.");
        }
        deactivateAllUserResetPasswordTokens(user);
        String token = StringUtil.generateRandomString(64);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusDays(1);
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, user.getId(), now, expiresAt, true);
        resetPasswordTokenRepository.save(resetPasswordToken);
        mailService.sendForgotPasswordEmail(user.getEmail(), token);
    }

    public ResetPasswordToken isResetPasswordTokenValid(String token) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid reset password token: " + token));
        if (resetPasswordToken.getActive() == false) {
            throw new BadRequestException("Reset password token is not active");
        }
        if (LocalDateTime.now().isAfter(resetPasswordToken.getExpiresAt())) {
            throw new BadRequestException("Reset password token is expired");
        }
        return resetPasswordToken;
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        ResetPasswordToken resetPasswordToken = isResetPasswordTokenValid(changePasswordRequest.token());
        User user = userRepository.findById(resetPasswordToken.getUserId())
                .orElseThrow(() -> new BadRequestException("User id " + resetPasswordToken.getUserId() + " doesn't exist"));
        user.setPassword(passwordEncoder.encode(changePasswordRequest.password()));
        userRepository.save(user);
        deactivateAllUserResetPasswordTokens(user);
    }

    private void deactivateAllUserResetPasswordTokens(User user) {
        List<ResetPasswordToken> resetPasswordTokens = resetPasswordTokenRepository.findByUserIdAndActiveTrue(user.getId());
        resetPasswordTokens.forEach(resetPasswordToken -> resetPasswordToken.setActive(false));
        resetPasswordTokenRepository.saveAll(resetPasswordTokens);
    }

    public UserResponse getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return new UserResponse(user.getId(), user.getEmail(), user.get_username(), user.getRole());
    }
}
