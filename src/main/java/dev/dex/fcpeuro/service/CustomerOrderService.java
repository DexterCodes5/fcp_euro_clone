package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.entity.customerorder.*;
import dev.dex.fcpeuro.model.customerorder.*;
import dev.dex.fcpeuro.repo.*;
import jakarta.mail.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final MailService mailService;

    public void receiveOrderGuest(CustomerOrderRequest customerOrderRequest) {
        CustomerOrder customerOrder = new CustomerOrder(customerOrderRequest);
        customerOrderRepository.save(customerOrder);
        try {
            mailService.sendOrderReceivedEmail(customerOrder);
        } catch (MessagingException ex) {
            throw new RuntimeException("Email sending failed: " + ex);
        }
    }

    public void receiveOrder(CustomerOrderRequest customerOrderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        CustomerOrder customerOrder = new CustomerOrder(customerOrderRequest);
        customerOrder.setUserId(user.getId());

        customerOrderRepository.save(customerOrder);

        try {
            mailService.sendOrderReceivedEmail(customerOrder);
        } catch (MessagingException ex) {
            throw new RuntimeException("Email sending failed: " + ex);
        }
    }
}
