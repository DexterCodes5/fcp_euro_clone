package dev.dex.fcpeuro.controller;

import com.fasterxml.jackson.databind.*;
import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.entity.auth.resetpasswordtoken.*;
import dev.dex.fcpeuro.model.auth.*;
import dev.dex.fcpeuro.repo.auth.*;
import org.assertj.core.api.recursive.comparison.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.security.crypto.password.*;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItUtil itUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @BeforeEach
    void setUp() {
        itUtil.deleteAll();
    }

    @Test
    void canRegister() throws Exception {
        // given
        String email = "mitko@mail.com";
        String username = "mitko";
        String password = "test123";
        RegisterRequest registerRequest = new RegisterRequest(email, username, password);

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)));

        // then
        resultActions.andExpect(status().isOk());
        User user = userRepository.findByEmail(email)
                .orElse(null);
        User expected = new User(null, email, username, null, Role.USER, false, null);
        assertThat(user)
                .usingRecursiveComparison()
                .ignoringFields("id", "password", "emailVerificationToken")
                .isEqualTo(expected);
        boolean passwordsMatch = passwordEncoder.matches(password, user.getPassword());
        assertThat(passwordsMatch).isTrue();
        assertThat(user.getEmailVerificationToken().length()).isEqualTo(64);
    }

    @Test
    void canVerifyEmail() throws Exception {
        // given
        String email = "mitko@mail.com";
        String username = "mitko";
        String password = "test123";
        String token = "emailVerificationToken";
        User user = new User(1, email, username, password, Role.USER, false, token);

        userRepository.save(user);

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/auth/verify-email?token=" + token));

        // then
        resultActions.andExpect(status().isOk());

        User actual = userRepository.findByEmail(email)
                .orElse(null);
        User expected = new User(1, email, username, password, Role.USER, true, null);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    void canAuthenticate() throws Exception {
        // given
        String email = "mitko@mail.com";
        String username = "mitko";
        String password = "test123";
        User user = new User(1, email, username, passwordEncoder.encode(password), Role.USER,
                true, null);
        AuthRequest authRequest = new AuthRequest(email, password, true);

        userRepository.save(user);

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)));

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andReturn();
        AuthResponse authResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                AuthResponse.class);
        assertThat(authResponse).isNotNull();
    }

    @Test
    void canRefreshToken() throws Exception {
        // given
        String email = "mitko@mail.com";
        String username = "mitko";
        String password = "test123";
        User user = new User(1, email, username, passwordEncoder.encode(password), Role.USER,
                true, null);
        AuthRequest authRequest = new AuthRequest(email, password, true);

        userRepository.save(user);

        MvcResult mvcResult = mockMvc
                .perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn();
        AuthResponse authResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                AuthResponse.class);

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/auth/refresh-token")
                        .header("Authorization", "Bearer " + authResponse.refreshToken()));

        // then
        MvcResult mvcResult1 = resultActions
                .andExpect(status().isOk())
                .andReturn();
        AuthResponse actual = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(),
                AuthResponse.class);
        assertThat(actual).isNotNull();
    }

    @Test
    void canForgotPassword() throws Exception {
        // given
        String email = "mitko@mail.com";
        String username = "mitko";
        String password = "test123";
        User user = new User(1, email, username, passwordEncoder.encode(password), Role.USER,
                true, null);

        userRepository.save(user);

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/auth/forgot-password?email=" + email));

        // then
        resultActions.andExpect(status().isOk());

        List<ResetPasswordToken> resetPasswordTokens = resetPasswordTokenRepository
                .findByUserIdAndActiveTrue(user.getId());
        ResetPasswordToken expected = new ResetPasswordToken(null, user.getId(), null,
                null, true);

        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("token", "createdAt", "expiresAt")
                .build();
        assertThat(resetPasswordTokens)
                .usingRecursiveFieldByFieldElementComparator(configuration)
                .contains(expected);
    }

    @Test
    void isResetPasswordTokenValid() throws Exception {
        // given
        String token = "token";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusDays(1);
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, 1, now,
                expiresAt, true);

        resetPasswordTokenRepository.save(resetPasswordToken);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/auth/is-reset-password-token-valid?token=" + token));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void canChangePassword() throws Exception {
        // given
        String email = "mitko@mail.com";
        String username = "mitko";
        String password = "test123";
        String token = "resetPasswordToken";
        String newPassword = "newPassword";
        User user = new User(1, email, username, passwordEncoder.encode(password), Role.USER,
                true, null);

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(token, newPassword);

        userRepository.save(user);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusDays(1);
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, user.getId(), now,
                expiresAt, true);
        resetPasswordTokenRepository.save(resetPasswordToken);

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/auth/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)));

        // then
        resultActions.andExpect(status().isOk());

        User actual = userRepository.findById(user.getId())
                .orElse(null);
        boolean passwordsMatch = passwordEncoder.matches(newPassword, actual.getPassword());
        assertThat(passwordsMatch).isTrue();
    }

    @Test
    void canGetUser() throws Exception {
        // given
        String email = "mitko@mail.com";
        String username = "mitko";
        String password = "test123";
        User user = new User(1, email, username, passwordEncoder.encode(password), Role.USER,
                true, null);
        AuthRequest authRequest = new AuthRequest(email, password, true);

        userRepository.save(user);

        MvcResult mvcResult = mockMvc
                .perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn();
        AuthResponse authResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                AuthResponse.class);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/auth/get-user")
                        .header("Authorization", "Bearer " + authResponse.accessToken()));

        // then
        MvcResult mvcResult1 = resultActions
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult1.getResponse().getContentAsString();
        UserResponse actual = objectMapper.readValue(contentAsString, UserResponse.class);
        UserResponse expected = new UserResponse(user.getId(), user.getEmail(), user.get_username(), user.getRole());

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}