package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;
import org.springframework.test.util.*;
import org.springframework.util.*;
import org.springframework.web.client.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RecaptchaServiceTest {
    @Mock
    private RestTemplate restTemplate;
    private RecaptchaService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RecaptchaService(restTemplate);
        ReflectionTestUtils.setField(underTest, "secretKey", "secret-key");
        ReflectionTestUtils.setField(underTest, "verifyUrl", "verify-url");
    }

    @Test
    void canValidateToken() {
        // given
        String recaptchaToken = "recaptcha-token";
        String verifyUrl = "verify-url";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", "secret-key");
        map.add("response", recaptchaToken);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        RecaptchaResponse recaptchaResponse = new RecaptchaResponse(true, "challenge",
                "hostname", 10.0, "action");
        ResponseEntity<RecaptchaResponse> response = new ResponseEntity<>(recaptchaResponse, HttpStatus.OK);

        given(restTemplate.exchange(verifyUrl, HttpMethod.POST, entity, RecaptchaResponse.class))
                .willReturn(response);

        // when
        RecaptchaResponse res = underTest.validateToken(recaptchaToken);

        // then
        ArgumentCaptor<String> urlArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpMethod> httpArgumentCaptor = ArgumentCaptor.forClass(HttpMethod.class);
        ArgumentCaptor<HttpEntity<MultiValueMap<String, String>>> entityArgumentCaptor =
                ArgumentCaptor.forClass(HttpEntity.class);
        ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);
        verify(restTemplate).exchange(urlArgumentCaptor.capture(), httpArgumentCaptor.capture(),
                entityArgumentCaptor.capture(), classArgumentCaptor.capture());
        assertThat(urlArgumentCaptor.getValue()).isEqualTo(verifyUrl);
        assertThat(httpArgumentCaptor.getValue()).isEqualTo(HttpMethod.POST);
        assertThat(entityArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(entity);
        assertThat(classArgumentCaptor.getValue()).isEqualTo(RecaptchaResponse.class);

        assertThat(res).isEqualTo(recaptchaResponse);
    }
}