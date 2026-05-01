package restaraunt.ex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private Long visitorId;

    private Long restaurantId;

    private int rating;

    // Текст отзыва может быть пустым
    private String text;
}
