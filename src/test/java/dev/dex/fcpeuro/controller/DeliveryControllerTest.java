package dev.dex.fcpeuro.controller;

import com.fasterxml.jackson.databind.*;
import dev.dex.fcpeuro.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
class DeliveryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItUtil itUtil;

    @BeforeEach
    void setUp() {
        itUtil.deleteAll();
    }

    @Test
    void canCalculateDeliveries() throws Exception {
        // given
        Address address = new Address("firstName", "lastName", "streetAddress",
                "streetAddressContd", "companyName", "city", "Bulgaria",
                "Sofia", "1000", "phoneNumber");

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)));

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Object actual = objectMapper.readValue(contentAsString, Object.class);
        assertThat(actual).isNotNull();
    }
}