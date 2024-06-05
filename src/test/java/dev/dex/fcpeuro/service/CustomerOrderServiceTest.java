package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.entity.customerorder.*;
import dev.dex.fcpeuro.model.customerorder.*;
import dev.dex.fcpeuro.repo.*;
import jakarta.mail.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerOrderServiceTest {
    @Mock
    private CustomerOrderRepository customerOrderRepository;
    @Mock
    private MailService mailService;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    private CustomerOrderService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerOrderService(customerOrderRepository, mailService);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void canReceiveOrderGuest() throws MessagingException {
        // given
        CustomerOrderRequest customerOrderRequest = new CustomerOrderRequest("email", "payment",
                10.0, 10.0, 0.0, 20.0, new ArrayList<>(), null,
                new DeliveryRequest(true, 10.0, LocalDate.now()));

        // when
        underTest.receiveOrderGuest(customerOrderRequest);

        // then
        ArgumentCaptor<CustomerOrder> customerOrderArgumentCaptor = ArgumentCaptor.forClass(CustomerOrder.class);
        verify(customerOrderRepository).save(customerOrderArgumentCaptor.capture());
        CustomerOrder expected = new CustomerOrder(customerOrderRequest);
        assertThat(customerOrderArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expected);

        verify(mailService).sendOrderReceivedEmail(customerOrderArgumentCaptor.capture());
        assertThat(customerOrderArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void willThrowInReceiveOrderGuestWhenSendEmailFail() throws MessagingException {
        // given
        CustomerOrderRequest customerOrderRequest = new CustomerOrderRequest("email", "payment",
                10.0, 10.0, 0.0, 20.0, new ArrayList<>(), null,
                new DeliveryRequest(true, 10.0, LocalDate.now()));

        MessagingException ex = new MessagingException("message");
        doThrow(ex).when(mailService).sendOrderReceivedEmail(any());

        // when
        // then
        assertThatThrownBy(() -> underTest.receiveOrderGuest(customerOrderRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email sending failed: " + ex);
    }


    @Test
    void canReceiveOrder() throws MessagingException {
        // given
        CustomerOrderRequest customerOrderRequest = new CustomerOrderRequest("email", "payment",
                10.0, 10.0, 0.0, 20.0, new ArrayList<>(), null,
                new DeliveryRequest(true, 10.0, LocalDate.now()));
        User user = new User(1, "email", "username", "password", Role.USER, true,
                null);

        given(securityContext.getAuthentication())
                .willReturn(authentication);
        given(authentication.getPrincipal())
                .willReturn(user);

        // when
        underTest.receiveOrder(customerOrderRequest);

        // then
        ArgumentCaptor<CustomerOrder> customerOrderArgumentCaptor = ArgumentCaptor.forClass(CustomerOrder.class);
        verify(customerOrderRepository).save(customerOrderArgumentCaptor.capture());
        CustomerOrder expected = new CustomerOrder(customerOrderRequest);
        expected.setUserId(1);
        assertThat(customerOrderArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expected);
        verify(mailService).sendOrderReceivedEmail(customerOrderArgumentCaptor.capture());
        assertThat(customerOrderArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void willThrowInReceiveOrder() throws MessagingException {
        // given
        CustomerOrderRequest customerOrderRequest = new CustomerOrderRequest("email", "payment",
                10.0, 10.0, 0.0, 20.0, new ArrayList<>(), null,
                new DeliveryRequest(true, 10.0, LocalDate.now()));
        User user = new User(1, "email", "username", "password", Role.USER, true,
                null);
        MessagingException ex = new MessagingException("message");

        given(securityContext.getAuthentication())
                .willReturn(authentication);
        given(authentication.getPrincipal())
                .willReturn(user);

        doThrow(ex).when(mailService).sendOrderReceivedEmail(any());

        // when
        // then
        assertThatThrownBy(() -> underTest.receiveOrder(customerOrderRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email sending failed: " + ex);
    }
}