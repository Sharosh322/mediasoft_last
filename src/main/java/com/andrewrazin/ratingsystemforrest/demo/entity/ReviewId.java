package com.andrewrazin.ratingsystemforrest.demo.entity;

import java.io.Serializable;
import java.util.Objects;

public class ReviewId implements Serializable {

    private Long visitorId;
    private Long restaurantId;

    public ReviewId() {
    }

    public ReviewId(Long visitorId, Long restaurantId) {
        this.visitorId = visitorId;
        this.restaurantId = restaurantId;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId reviewId)) return false;
        return Objects.equals(visitorId, reviewId.visitorId)
                && Objects.equals(restaurantId, reviewId.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitorId, restaurantId);
    }
}
