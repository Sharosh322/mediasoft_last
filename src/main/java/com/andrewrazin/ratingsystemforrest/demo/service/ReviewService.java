package com.andrewrazin.ratingsystemforrest.demo.service;

import com.andrewrazin.ratingsystemforrest.demo.entity.Review;
import com.andrewrazin.ratingsystemforrest.demo.entity.ReviewId;
import com.andrewrazin.ratingsystemforrest.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantService restaurantService;
    private final VisitorService visitorService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         RestaurantService restaurantService,
                         VisitorService visitorService) {
        this.reviewRepository = reviewRepository;
        this.restaurantService = restaurantService;
        this.visitorService = visitorService;
    }

    @Transactional
    public Review save(Review review) {
        if (!visitorService.existsById(review.getVisitorId())) {
            throw new RuntimeException("Visitor not found with id: " + review.getVisitorId());
        }

        if (!restaurantService.existsById(review.getRestaurantId())) {
            throw new RuntimeException("Restaurant not found with id: " + review.getRestaurantId());
        }

        Review savedReview = reviewRepository.save(review);
        recalculateRestaurantRating(review.getRestaurantId());
        return savedReview;
    }

    @Transactional
    public void remove(Long visitorId, Long restaurantId) {
        ReviewId reviewId = new ReviewId(visitorId, restaurantId);
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);

        if (reviewOpt.isPresent()) {
            reviewRepository.deleteById(reviewId);
            recalculateRestaurantRating(restaurantId);
        }
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Page<Review> findAllPageable(int page, int size, String direction) {
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "rating"));
        return reviewRepository.findAll(pageable);
    }

    public Optional<Review> findById(Long visitorId, Long restaurantId) {
        return reviewRepository.findById(new ReviewId(visitorId, restaurantId));
    }

    public List<Review> findByRestaurantId(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    public Page<Review> findByRestaurantIdPageable(Long restaurantId, int page, int size, String direction) {
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "rating"));
        return reviewRepository.findByRestaurantId(restaurantId, pageable);
    }

    public List<Review> findByVisitorId(Long visitorId) {
        return reviewRepository.findByVisitorId(visitorId);
    }

    public boolean existsByVisitorIdAndRestaurantId(Long visitorId, Long restaurantId) {
        return reviewRepository.existsByVisitorIdAndRestaurantId(visitorId, restaurantId);
    }

    private void recalculateRestaurantRating(Long restaurantId) {
        Double averageRating = reviewRepository.calculateAverageRatingByRestaurantId(restaurantId);
        restaurantService.updateRating(restaurantId, averageRating);
    }
}
