package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.model.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.client.*;



@Service
@RequiredArgsConstructor
public class RecaptchaService {
    @Value("${recaptcha.secretKey}")
    private String secretKey;
    @Value("${recaptcha.verifyUrl}")
    private String verifyUrl;
    private final RestTemplate restTemplate;

    public RecaptchaResponse validateToken(String recaptchaToken) {
        // https://www.google.com/recaptcha/api/siteverify METHOD: POST
        // secret	Required. The shared key between your site and reCAPTCHA.
        // response Required. The user response token provided by the reCAPTCHA client-side integration on your site.

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secretKey);
        map.add("response", recaptchaToken);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<RecaptchaResponse> response = restTemplate.exchange(
                verifyUrl,
                HttpMethod.POST,
                entity,
                RecaptchaResponse.class
        );
        return response.getBody();
    }
}
