package dev.dex.fcpeuro.service;

import com.fasterxml.jackson.databind.*;
import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.entity.auth.resetpasswordtoken.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.model.auth.*;
import dev.dex.fcpeuro.repo.auth.*;
import jakarta.mail.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.crypto.password.*;

import java.io.*;
import java.nio.charset.*;
import java.time.*;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AccessTokenRepository accessTokenRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private MailService mailService;
    @Mock
    private ResetPasswordTokenRepository resetPasswordTokenRepository;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    private AuthService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthService(userRepository, passwordEncoder, jwtService, accessTokenRepository,
                refreshTokenRepository, authenticationManager, mailService, resetPasswordTokenRepository);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void canRegister() throws MessagingException {
        // given
        String email = "email";
        String username = "username";
        String password = "password";
        RegisterRequest registerRequest = new RegisterRequest(email ,username, password);
        given(userRepository.selectExistsEmail(anyString()))
                .willReturn(false);
        given(userRepository.selectExistsUsername(anyString()))
                .willReturn(false);

        // when
        underTest.register(registerRequest);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User expectedUser = new User(null, email, username, null, Role.USER, false, null);
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .ignoringFields("id", "password", "emailVerificationToken")
                .isEqualTo(expectedUser);

        verify(mailService).sendRegisterEmail(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .ignoringFields("id", "password", "emailVerificationToken")
                .isEqualTo(expectedUser);
    }

    @Test
    void willThrowInRegisterWhenEmailIsTaken() throws MessagingException {
        // given
        String email = "email";
        String username = "username";
        String password = "password";
        RegisterRequest registerRequest = new RegisterRequest(email ,username, password);
        given(userRepository.selectExistsEmail(anyString()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.register(registerRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + registerRequest.email() + " is already taken");
        verify(userRepository, never()).selectExistsUsername(anyString());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verify(mailService, never()).sendRegisterEmail(any());
    }

    @Test
    void willThrowInRegisterWhenUsernameIsTaken() throws MessagingException {
        // given
        String email = "email";
        String username = "username";
        String password = "password";
        RegisterRequest registerRequest = new RegisterRequest(email ,username, password);
        given(userRepository.selectExistsUsername(anyString()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.register(registerRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Username " + registerRequest.username() + " is already taken");
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verify(mailService, never()).sendRegisterEmail(any());
    }

    @Test
    void verifyEmail() {
        // given
        String token = "token";
        User user = new User(1, "email", "username", "password",
                Role.USER, false, token);
        given(userRepository.findByEmailVerificationToken(anyString()))
                .willReturn(Optional.of(user));

        // when
        underTest.verifyEmail(token);

        // then
        verify(userRepository).findByEmailVerificationToken(token);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User expectedUser = new User(1, "email", "username", "password",
                Role.USER, true, null);
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .ignoringFields("emailVerificationToken")
                .isEqualTo(expectedUser);
    }

    @Test
    void willThrowInVerifyEmailWhenTokenIsInvalid() {
        // given
        String token = "token";

        // when
        // then
        assertThatThrownBy(() -> underTest.verifyEmail(token))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid email verification token");
        verify(userRepository, never()).save(any());
    }

    @Test
    void canAuthenticate() {
        // given
        String email = "email";
        String password = "password";
        boolean rememberMe = true;
        AuthRequest authRequest = new AuthRequest(email, password, rememberMe);
        User user = new User(1, email, "username", password, Role.USER, true, null);
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(user));
        String accessToken = "access-token";
        given(jwtService.generateAccessToken(any()))
                .willReturn(accessToken);
        String refreshToken = "refresh-token";
        given(jwtService.generateRefreshToken(any(), anyBoolean()))
                .willReturn(refreshToken);

        // when
        AuthResponse res = underTest.authenticate(authRequest);

        // then
        AuthResponse expected = new AuthResponse(accessToken, refreshToken);
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void canRefreshToken() throws IOException {
        // given
        String accessToken = "access-token";
        String refreshToken = "refresh-token";
        String newRefreshToken = "new-refresh-token";
        String authorization = "Bearer " + refreshToken;
        String email = "email";
        User user = new User(1, email, "username", "password", Role.USER,
                true, null);
        RefreshToken refreshTokenEntity = new RefreshToken(1, 1, refreshToken, false,
                false, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        given(request.getHeader(HttpHeaders.AUTHORIZATION))
                .willReturn(authorization);
        given(jwtService.extractEmail(anyString()))
                .willReturn(email);
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(user));
        given(refreshTokenRepository.findByRefreshToken(anyString()))
                .willReturn(Optional.of(refreshTokenEntity));
        given(jwtService.isTokenValid(anyString(), any()))
                .willReturn(true);
        given(jwtService.generateAccessToken(any()))
                .willReturn(accessToken);
        given(jwtService.generateRefreshToken(any(), anyBoolean()))
                .willReturn(newRefreshToken);
        given(response.getOutputStream())
                .willReturn(new DelegatingServletOutputStream(out));

        // when
        underTest.refreshToken(request, response);

        // then
        verify(jwtService).extractEmail(refreshToken);
        verify(userRepository).findByEmail(email);
        verify(refreshTokenRepository).findByRefreshToken(refreshToken);
        verify(jwtService).isTokenValid(refreshToken, user);
        verify(jwtService).generateAccessToken(user);
        verify(jwtService).generateRefreshToken(user, true);

        AuthResponse expectedAuthResponse = new AuthResponse(accessToken, newRefreshToken);
        AuthResponse actualAuthResponse = new ObjectMapper().readValue(out.toString(StandardCharsets.UTF_8), AuthResponse.class);

        assertThat(actualAuthResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthResponse);
    }

    @Test
    void willThrowInRefreshTokenWhenEmailInvalid() throws IOException {
        // given
        String refreshToken = "refresh-token";
        String authorization = "Bearer " + refreshToken;
        String email = "email";

        given(request.getHeader(HttpHeaders.AUTHORIZATION))
                .willReturn(authorization);
        given(jwtService.extractEmail(anyString()))
                .willReturn(email);

        // when
        // then
        assertThatThrownBy(() -> underTest.refreshToken(request, response))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email from token is invalid.");

        verify(refreshTokenRepository, never()).findByRefreshToken(anyString());
        verify(jwtService, never()).isTokenValid(anyString(), any());
        verify(jwtService, never()).generateAccessToken(any());
        verify(jwtService, never()).generateRefreshToken(any(), anyBoolean());
    }

    private static class DelegatingServletOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream targetStream;

        public DelegatingServletOutputStream(ByteArrayOutputStream targetStream) {
            this.targetStream = targetStream;
        }

        @Override
        public void write(int b) throws IOException {
            targetStream.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // No-op
        }
    }

    @Test
    void canForgotPassword() throws MessagingException {
        // given
        String email = "email";
        User user = new User(1, email, "username", "password", Role.USER,
                true, null);

        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(user));

        // when
        underTest.forgotPassword(email);

        // then
        ArgumentCaptor<ResetPasswordToken> resetPasswordTokenArgumentCaptor = ArgumentCaptor.forClass(ResetPasswordToken.class);
        verify(resetPasswordTokenRepository).save(resetPasswordTokenArgumentCaptor.capture());
        assertThat(resetPasswordTokenArgumentCaptor.getValue()).isInstanceOf(ResetPasswordToken.class);

        verify(mailService).sendForgotPasswordEmail(eq(email), anyString());
    }

    @Test
    void willThrowInForgotPasswordWhenEmailInvalid() throws MessagingException {
        String email = "email";

        // when
        // then
        assertThatThrownBy(() -> underTest.forgotPassword(email))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("User email " + email + " doesn't exist");

        verify(resetPasswordTokenRepository, never()).save(any());
        verify(mailService, never()).sendForgotPasswordEmail(anyString(), anyString());
    }

    @Test
    void willThrowInForgotPasswordWhenNotActive() throws MessagingException {
        String email = "email";
        User user = new User(1, email, "username", "password", Role.USER,
                false, null);

        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(user));

        // when
        // then
        assertThatThrownBy(() -> underTest.forgotPassword(email))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("User hasn't validated his email address.");

        verify(resetPasswordTokenRepository, never()).save(any());
        verify(mailService, never()).sendForgotPasswordEmail(anyString(), anyString());
    }

    @Test
    void canIsResetPasswordTokenValid() {
        // given
        String token = "reset-password-token";
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, 1, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), true);

        given(resetPasswordTokenRepository.findByToken(token))
                .willReturn(Optional.of(resetPasswordToken));

        // when
        ResetPasswordToken res = underTest.isResetPasswordTokenValid(token);

        // then
        assertThat(res).isEqualTo(resetPasswordToken);
    }

    @Test
    void willThrowInIsResetPasswordTokenValidWhenTokenInvalid() {
        // given
        String token = "reset-password-token";

        // when
        // then
        assertThatThrownBy(() -> underTest.isResetPasswordTokenValid(token))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid reset password token: " + token);
    }

    @Test
    void willThrowInIsResetPasswordTokenValidWhenTokenNotActive() {
        // given
        String token = "reset-password-token";
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, 1, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), false);

        given(resetPasswordTokenRepository.findByToken(anyString()))
                .willReturn(Optional.of(resetPasswordToken));

        // when
        // then
        assertThatThrownBy(() -> underTest.isResetPasswordTokenValid(token))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Reset password token is not active");
    }

    @Test
    void willThrowInIsResetPasswordTokenValidWhenTokenExpired() {
        // given
        String token = "reset-password-token";
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, 1, LocalDateTime.now(),
                LocalDateTime.now().minusDays(1), true);

        given(resetPasswordTokenRepository.findByToken(anyString()))
                .willReturn(Optional.of(resetPasswordToken));

        // when
        // then
        assertThatThrownBy(() -> underTest.isResetPasswordTokenValid(token))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Reset password token is expired");
    }

    @Test
    void canChangePassword() {
        // given
        String token = "token";
        String encodedPassword = "encodedPassword";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(token, "password");
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, 1, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), true);
        User user = new User(1, "email", "username", "password", Role.USER, true,
                null);

        given(resetPasswordTokenRepository.findByToken(anyString()))
                .willReturn(Optional.of(resetPasswordToken));
        given(userRepository.findById(anyInt()))
                .willReturn(Optional.of(user));
        given(passwordEncoder.encode(any()))
                .willReturn(encodedPassword);
        given(resetPasswordTokenRepository.findByUserIdAndActiveTrue(anyInt()))
                .willReturn(List.of(resetPasswordToken));

        // when
        underTest.changePassword(changePasswordRequest);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User expectedUser = new User(1, "email", "username", encodedPassword, Role.USER, true,
                null);
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedUser);

        ArgumentCaptor<List<ResetPasswordToken>> resetPasswordsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(resetPasswordTokenRepository).saveAll(resetPasswordsArgumentCaptor.capture());
        assertThat(resetPasswordsArgumentCaptor.getValue().get(0).getActive()).isFalse();
    }

    @Test
    void willThrowInChangePasswordWhenUserIdInvalid() {
        // given
        String token = "token";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(token, "password");
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, 1, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), true);

        given(resetPasswordTokenRepository.findByToken(anyString()))
                .willReturn(Optional.of(resetPasswordToken));

        // when
        // then
        assertThatThrownBy(() -> underTest.changePassword(changePasswordRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("User id " + resetPasswordToken.getUserId() + " doesn't exist");
    }

    @Test
    void canGetUser() {
        // given
        User user = new User(1, "email", "username", "password", Role.USER, true,
                null);

        given(securityContext.getAuthentication())
                .willReturn(authentication);
        given(authentication.getPrincipal())
                .willReturn(user);

        // when
        UserResponse res = underTest.getUser();

        // then
        UserResponse expected = new UserResponse(user.getId(), user.getEmail(), user.get_username(), user.getRole());
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}