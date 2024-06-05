package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.customerorder.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.mail.javamail.*;
import org.springframework.test.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;
    private MailService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MailService(javaMailSender);
        ReflectionTestUtils.setField(underTest, "frontendUrl", "frontend-url");
    }

    @Test
    void canSendOrderReceivedEmail() throws MessagingException {
        // given
        String email = "email";
        CustomerOrder customerOrder = new CustomerOrder(1, email, "payment", 10.0, 10.0, 0.0, 20.0,
                1, null, null, null);
        MimeMessage mimeMessage = mock(MimeMessage.class);

        given(javaMailSender.createMimeMessage())
                .willReturn(mimeMessage);

        // when
        underTest.sendOrderReceivedEmail(customerOrder);

        // then
        ArgumentCaptor<MimeMessage> mimeMessageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(javaMailSender).send(mimeMessageArgumentCaptor.capture());
        MimeMessage capturedMimeMessage = mimeMessageArgumentCaptor.getValue();
        MimeMessageHelper helper = new MimeMessageHelper(capturedMimeMessage, "utf-8");
        assertThat(helper).isNotNull();
//        assertThat(capturedMimeMessage.getFrom()).isEqualTo(email);
    }
}