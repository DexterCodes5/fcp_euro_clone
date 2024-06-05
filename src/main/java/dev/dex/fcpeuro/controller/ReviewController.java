package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.service.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{partId}")
    public ResponseEntity<?> getReviews(@PathVariable Integer partId) {
        return ResponseEntity.ok(reviewService.findByPartId(partId));
    }

    @PostMapping
    public ResponseEntity<?> postReview(@Valid @RequestBody Review review) {
        reviewService.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
