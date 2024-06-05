package dev.dex.fcpeuro.controller;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.model.auth.*;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItUtil itUtil;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        itUtil.deleteAll();
    }

    @Test
    void canGetReviews() throws Exception {
        // given
        Part part = new Part(1, "title", "url", "sku", 123, 10, 10.0, "OE",
                null, false, null, null, false, null,
                null, null, new ArrayList<>(), null, new ArrayList<>());
        partRepository.save(part);

        User user = new User(1, "email", "username", "password",
                Role.USER, true, null);
        userRepository.save(user);

        Review review = new Review(null, part.getId(), user.getId(), Instant.now(), "name", 5,
                "title", "texttexttext");
        Review review2 = new Review(null, part.getId(), user.getId(), Instant.now(), "name1", 5,
                "title1", "texttexttexttext");
        reviewRepository.save(review);
        reviewRepository.save(review2);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/reviews/" + part.getId()));

        // then
        String reviewsJson = resultActions.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Review> actual = objectMapper.readValue(reviewsJson, new TypeReference<>(){});
        List<Review> expected = new ArrayList<>(List.of(review, review2));
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt")
                .isEqualTo(expected);
    }

    @Test
    void canPostReview() throws Exception {
        // given
        String email = "mitko@mail.com";
        String username = "mitko";
        String password = "test123";
        User user = new User(1, email, username, passwordEncoder.encode(password), Role.USER,
                true, null);
        AuthRequest authRequest = new AuthRequest(email, password, true);

        userRepository.save(user);

        Part part = new Part(1, "title", "url", "sku", 123, 10, 10.0, "OE",
                null, false, null, null, false, null,
                null, null, new ArrayList<>(), null, new ArrayList<>());
        partRepository.save(part);

        Review review = new Review(null, part.getId(), null, null, "name", 5,
                "title1", "texttexttext");

        MvcResult mvcResult = mockMvc
                .perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn();
        AuthResponse authResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                AuthResponse.class);

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/reviews")
                        .header("Authorization", "Bearer " + authResponse.accessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)));

        // then
        resultActions.andExpect(status().isCreated());

        List<Review> reviews = reviewRepository.findAll();
        Review expected = new Review(null, part.getId(), user.getId(), null, "name", 5,
                "title1", "texttexttext");
        assertThat(reviews)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt")
                .contains(expected);
    }
}