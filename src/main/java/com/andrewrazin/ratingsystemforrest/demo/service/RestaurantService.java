package com.andrewrazin.ratingsystemforrest.demo.service;

import com.andrewrazin.ratingsystemforrest.demo.entity.Restaurant;
import com.andrewrazin.ratingsystemforrest.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void remove(Long id) {
        restaurantRepository.deleteById(id);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return restaurantRepository.existsById(id);
    }

    public List<Restaurant> findByRatingGreaterThanEqual(BigDecimal minRating) {
        return restaurantRepository.findByRatingGreaterThanEqual(minRating);
    }

    public List<Restaurant> findByRatingGreaterThanEqualJpql(BigDecimal minRating) {
        return restaurantRepository.findByRatingGreaterThanEqualJpql(minRating);
    }

    public void updateRating(Long restaurantId, Double newRating) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));

        if (newRating == null) {
            restaurant.setRating(null);
        } else {
            restaurant.setRating(BigDecimal.valueOf(newRating).setScale(2, RoundingMode.HALF_UP));
        }

        restaurantRepository.save(restaurant);
    }
}
