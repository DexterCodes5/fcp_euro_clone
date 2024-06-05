package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.entity.customerorder.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    @Value("${frontend-url}")
    private String frontendUrl;
    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendOrderReceivedEmail(CustomerOrder customerOrder) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMessage = """
                <h3>Thank you for your order.</h3>
                <p>We have received your order and we are going to process it as soon as we can.</p>
                <p>Order number: ?1</p>
                <p>Best regards,</p>
                <p>FCP Euro Team</p>
                """;
        htmlMessage = htmlMessage.replace("?1", String.valueOf(customerOrder.getId()));
        helper.setText(htmlMessage, true);
        helper.setTo(customerOrder.getEmail());
        helper.setSubject("Order " + customerOrder.getId() + " received");
        helper.setFrom(emailFrom);
        javaMailSender.send(mimeMessage);
    }

    public void sendRegisterEmail(User user) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(emailFrom);
        helper.setTo(user.getEmail());
        String subject = "Email verification";
        String verificationUrl = frontendUrl + "/verify-email?token=" + user.getEmailVerificationToken();
        String htmlMessage = "<p>Hello " + user.get_username() + ",<br>"
                + "Verify your email address with the link below:</p>"
                +"<a href=\"" + verificationUrl + "\">" + verificationUrl + "</a>";
        helper.setSubject(subject);
        helper.setText(htmlMessage, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendForgotPasswordEmail(String email, String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(emailFrom);
        helper.setTo(email);
        String subject = "FCP Euro Reset password instructions";
        String resetPasswordUrl = frontendUrl + "/change-password?token=" + token;
        String htmlMessage = ""
                + "<p>A request to reset your password has been made.</p>"
                + "<p>"
                    + "If you recently used the guest checkout option on our website, setting a password below will enable you to use the pain-free online returns system."
                    + "Otherwise, an FCP Euro employee may have completed the checkout process for you, or you requested to reset your existing password."
                + "</p>"
                + "<a href=\"" + resetPasswordUrl + "\">" + resetPasswordUrl + "</a>"
                + "<p>Reset link will expire after 6 hours.</p>"
                + "<p>If none of the options above apply to you, please ignore this email.</p>"
                + "<p>If the above URL does not work try copying and pasting it into your browser.<br>"
                + "If you continue to have problems please feel free to contact us.</p>";
        helper.setSubject(subject);
        helper.setText(htmlMessage, true);
        javaMailSender.send(mimeMessage);
    }
}
