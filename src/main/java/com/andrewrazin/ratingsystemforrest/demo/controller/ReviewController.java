package com.andrewrazin.ratingsystemforrest.demo.controller;

import com.andrewrazin.ratingsystemforrest.demo.entity.Review;
import com.andrewrazin.ratingsystemforrest.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        try {
            Review savedReview = reviewService.save(review);
            return ResponseEntity.ok(savedReview);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.findAll();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Review>> getReviewsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(reviewService.findAllPageable(page, size, direction));
    }

    @GetMapping("/{visitorId}/{restaurantId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long visitorId,
                                                @PathVariable Long restaurantId) {
        return reviewService.findById(visitorId, restaurantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Review>> getReviewsByRestaurant(@PathVariable Long restaurantId) {
        List<Review> reviews = reviewService.findByRestaurantId(restaurantId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/restaurant/{restaurantId}/page")
    public ResponseEntity<Page<Review>> getReviewsByRestaurantPage(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(reviewService.findByRestaurantIdPageable(restaurantId, page, size, direction));
    }

    @GetMapping("/visitor/{visitorId}")
    public ResponseEntity<List<Review>> getReviewsByVisitor(@PathVariable Long visitorId) {
        List<Review> reviews = reviewService.findByVisitorId(visitorId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{visitorId}/{restaurantId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long visitorId,
                                             @PathVariable Long restaurantId) {
        reviewService.remove(visitorId, restaurantId);
        return ResponseEntity.ok().build();
    }
}
