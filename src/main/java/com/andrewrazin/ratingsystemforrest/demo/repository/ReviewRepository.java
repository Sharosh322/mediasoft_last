package com.andrewrazin.ratingsystemforrest.demo.repository;

import com.andrewrazin.ratingsystemforrest.demo.entity.Review;
import com.andrewrazin.ratingsystemforrest.demo.entity.ReviewId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

    List<Review> findByRestaurantId(Long restaurantId);

    List<Review> findByVisitorId(Long visitorId);

    Page<Review> findByRestaurantId(Long restaurantId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.restaurantId = :restaurantId")
    Double calculateAverageRatingByRestaurantId(@Param("restaurantId") Long restaurantId);

    boolean existsByVisitorIdAndRestaurantId(Long visitorId, Long restaurantId);
}
