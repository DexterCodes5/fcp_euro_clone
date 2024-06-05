package dev.dex.fcpeuro.config;

import dev.dex.fcpeuro.model.*;
import dev.dex.fcpeuro.service.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.slf4j.*;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;

@Component
@RequiredArgsConstructor
public class RecaptchaFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(RecaptchaFilter.class);
    private final RecaptchaService recaptchaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("POST") && request.getRequestURI() == "/api/v1/auth/login") {
            String recaptcha = request.getHeader("recaptcha");
            RecaptchaResponse recaptchaResponse = recaptchaService.validateToken(recaptcha);
            if (!recaptchaResponse.success()) {
                LOG.info("Invalid reCAPTCHA token");
                throw new BadCredentialsException("Invalid reCAPTCHA token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
