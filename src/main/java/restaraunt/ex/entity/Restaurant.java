package restaraunt.ex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaraunt.ex.enums.CuisineType;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    private Long id;

    private String name;

    // Описание может быть пустым
    private String description;

    private CuisineType cuisineType;

    private BigDecimal averageCheck;

    private BigDecimal userRating;
}
