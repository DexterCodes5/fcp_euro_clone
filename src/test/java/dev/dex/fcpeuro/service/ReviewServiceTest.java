package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.repo.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PartRepository partRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    private ReviewService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ReviewService(reviewRepository, partRepository);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void canFindByPartId() {
        // given
        Integer partId = 1;

        // when
        underTest.findByPartId(partId);

        // then
        verify(reviewRepository).findByPartId(partId);
    }

    @Test
    void canSave() {
        // given
        Review review = new Review(1, 1, null, null, "name", 5, "title",
                "text");
        User user = new User(1, "email", "username", "password", Role.USER, true,
                null);

        given(securityContext.getAuthentication())
                .willReturn(authentication);
        given(authentication.getPrincipal())
                .willReturn(user);
        given(partRepository.findById(anyInt()))
                .willReturn(Optional.of(new Part()));

        // when
        underTest.save(review);

        // then
        ArgumentCaptor<Review> reviewArgumentCaptor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository).save(reviewArgumentCaptor.capture());
        Review captured = reviewArgumentCaptor.getValue();
        Review expected = new Review(1, 1, 1, null, "name", 5, "title",
                "text");
        assertThat(captured)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(expected);
    }

    @Test
    void saveWillThrowWhenPartIdInvalid() {
        // given
        Review review = new Review(1, 1, null, null, "name", 5, "title",
                "text");
        User user = new User(1, "email", "username", "password", Role.USER, true,
                null);

        given(securityContext.getAuthentication())
                .willReturn(authentication);
        given(authentication.getPrincipal())
                .willReturn(user);

        // when
        // then
        assertThatThrownBy(() -> underTest.save(review))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid product id: " + review.getPartId());
        verify(reviewRepository, never())
                .save(any());
    }
}