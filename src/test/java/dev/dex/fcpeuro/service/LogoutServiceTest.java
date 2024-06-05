package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.repo.auth.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServiceTest {
    @Mock
    private AccessTokenRepository accessTokenRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Authentication authentication;
    private LogoutService underTest;

    @BeforeEach
    void setUp() {
        underTest = new LogoutService(accessTokenRepository, refreshTokenRepository);
    }

    @Test
    void canLogout() {
        // given
        String accessToken = "access-token";
        String refreshToken = "refresh-token";
        String authorization = "Bearer " + accessToken;
        List<AccessToken> accessTokens = new ArrayList<>(List.of(
                new AccessToken(1, 1, accessToken, false, false),
                new AccessToken(2, 1, accessToken+"2", false, false)
        ));
        List<RefreshToken> refreshTokens = new ArrayList<>(List.of(
                new RefreshToken(1, 1, refreshToken, false, false, true),
                new RefreshToken(2, 1, refreshToken, false, false, true)
        ));

        given(request.getHeader(HttpHeaders.AUTHORIZATION))
                .willReturn(authorization);
        given(accessTokenRepository.findFirstByAccessTokenOrderByIdDesc(anyString()))
                .willReturn(Optional.of(accessTokens.get(0)));
        given(accessTokenRepository.findAllValidTokensByUserId(anyInt()))
                .willReturn(accessTokens);
        given(refreshTokenRepository.findAllValidTokensByUserId(anyInt()))
                .willReturn(refreshTokens);

        // when
        underTest.logout(request, response, authentication);

        // then
        ArgumentCaptor<List<AccessToken>> accessTokensArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(accessTokenRepository).saveAll(accessTokensArgumentCaptor.capture());
        List<AccessToken> expectedAccessTokens = new ArrayList<>(List.of(
                new AccessToken(1, 1, accessToken, true, true),
                new AccessToken(2, 1, accessToken+"2", true, true)
        ));
        assertThat(accessTokensArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedAccessTokens);

        ArgumentCaptor<List<RefreshToken>> refreshTokensArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(refreshTokenRepository).saveAll(refreshTokensArgumentCaptor.capture());
        List<RefreshToken> expectedRefreshTokens = new ArrayList<>(List.of(
                new RefreshToken(1, 1, refreshToken, true, true, true),
                new RefreshToken(2, 1, refreshToken, true, true, true)
        ));
        assertThat(refreshTokensArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedRefreshTokens);
    }

    @Test
    void willThrowWhenAccessTokenInvalid() {
        // given
        String accessToken = "access-token";
        String authorization = "Bearer " + accessToken;

        given(request.getHeader(HttpHeaders.AUTHORIZATION))
                .willReturn(authorization);

        // when
        // then
        assertThatThrownBy(() -> underTest.logout(request, response, authentication))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Invalid jwt token");

        verify(accessTokenRepository, never()).findAllValidTokensByUserId(anyInt());
        verify(accessTokenRepository, never()).saveAll(any());
        verify(refreshTokenRepository, never()).findAllValidTokensByUserId(anyInt());
        verify(refreshTokenRepository, never()).saveAll(any());
    }
}