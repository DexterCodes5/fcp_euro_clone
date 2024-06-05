package dev.dex.fcpeuro.controller;

import com.fasterxml.jackson.databind.*;
import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.entity.customerorder.*;
import dev.dex.fcpeuro.model.auth.*;
import dev.dex.fcpeuro.model.customerorder.*;
import dev.dex.fcpeuro.repo.*;
import dev.dex.fcpeuro.repo.auth.*;
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
class CustomerOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItUtil itUtil;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        itUtil.deleteAll();
    }

    @Test
    void canReceiveOrderRequestGuest() throws Exception {
        // given
        CustomerOrderRequest customerOrderRequest = new CustomerOrderRequest("mitko@mail.com", "payment",
                10.0, 10.0, 0.0, 20.0, new ArrayList<>(), null,
                new DeliveryRequest(true, 0d, LocalDate.now()));

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/customer-orders/receive-order-request-guest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerOrderRequest)));

        // then
        resultActions.andExpect(status().isCreated());
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        CustomerOrder expected = new CustomerOrder(customerOrderRequest);
        assertThat(customerOrders)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "cartItems", "delivery")
                .contains(expected);
    }

    @Test
    void canReceiveOrderRequest() throws Exception {
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

        CustomerOrderRequest customerOrderRequest = new CustomerOrderRequest("mitko@mail.com", "payment",
                10.0, 10.0, 0.0, 20.0, new ArrayList<>(), null,
                new DeliveryRequest(true, 0d, LocalDate.now()));

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/customer-orders/receive-order-request")
                        .header("Authorization", "Bearer " + authResponse.accessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerOrderRequest)));

        // then
        resultActions.andExpect(status().isCreated());
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        CustomerOrder expected = new CustomerOrder(customerOrderRequest);
        expected.setUserId(user.getId());
        assertThat(customerOrders)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "cartItems", "delivery")
                .contains(expected);
    }
}