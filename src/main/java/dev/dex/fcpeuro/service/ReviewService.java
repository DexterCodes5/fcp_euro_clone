package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.auth.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.repo.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PartRepository partRepository;

    public List<Review> findByPartId(Integer partId) {
        return reviewRepository.findByPartId(partId);
    }

    public void save(Review review) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        review.setUserId(user.getId());
        partRepository.findById(review.getPartId())
                        .orElseThrow(() -> new BadRequestException("Invalid product id: " + review.getPartId()));
        review.setCreatedAt(Instant.now());
        reviewRepository.save(review);
    }
}
