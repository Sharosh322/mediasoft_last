package restaraunt.ex.repository;

import org.springframework.stereotype.Repository;
import restaraunt.ex.entity.Review;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {

    private final List<Review> reviews = new ArrayList<>();

    public void save(Review review) {
        reviews.add(review);
    }

    public void remove(Review review) {
        reviews.remove(review);
    }

    public List<Review> findAll() {
        return reviews;
    }

    public List<Review> findById(Long restaurantId) {
        return reviews.stream()
                .filter(review -> review.getRestaurantId().equals(restaurantId))
                .toList();
    }
}
