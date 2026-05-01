package restaraunt.ex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restaraunt.ex.entity.Review;
import restaraunt.ex.repository.RestaurantRepository;
import restaraunt.ex.repository.ReviewRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public void save(Review review) {
        reviewRepository.save(review);
        recalculateRestaurantRating(review.getRestaurantId());
    }

    public void remove(Review review) {
        reviewRepository.remove(review);
        recalculateRestaurantRating(review.getRestaurantId());
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    private void recalculateRestaurantRating(Long restaurantId) {
        List<Review> restaurantReviews = reviewRepository.findById(restaurantId);

        BigDecimal rating;

        if (restaurantReviews.isEmpty()) {
            rating = BigDecimal.ZERO;
        } else {
            double average = restaurantReviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0);

            rating = BigDecimal.valueOf(average)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        restaurantRepository.findAll()
                .stream()
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .findFirst()
                .ifPresent(restaurant -> restaurant.setUserRating(rating));
    }
}
