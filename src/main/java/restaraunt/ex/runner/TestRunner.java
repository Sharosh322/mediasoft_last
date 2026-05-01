package restaraunt.ex.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import restaraunt.ex.entity.Restaurant;
import restaraunt.ex.entity.Review;
import restaraunt.ex.entity.Visitor;
import restaraunt.ex.enums.CuisineType;
import restaraunt.ex.service.RestaurantService;
import restaraunt.ex.service.ReviewService;
import restaraunt.ex.service.VisitorService;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TestRunner implements CommandLineRunner {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @Override
    public void run(String... args) {

        Visitor visitor1 = new Visitor(1L, "Иван", 21, "Мужской");
        Visitor visitor2 = new Visitor(2L, null, 25, "Женский");
        Visitor visitor3 = new Visitor(3L, "Анна", 19, "Женский");

        visitorService.save(visitor1);
        visitorService.save(visitor2);
        visitorService.save(visitor3);

        Restaurant restaurant1 = new Restaurant(
                1L,
                "La Pasta",
                "Итальянский ресторан",
                CuisineType.ITALIAN,
                BigDecimal.valueOf(1500),
                BigDecimal.ZERO
        );

        Restaurant restaurant2 = new Restaurant(
                2L,
                "Dragon Food",
                "Китайская кухня",
                CuisineType.CHINESE,
                BigDecimal.valueOf(1200),
                BigDecimal.ZERO
        );

        restaurantService.save(restaurant1);
        restaurantService.save(restaurant2);

        reviewService.save(new Review(1L, 1L, 5, "Очень вкусно"));
        reviewService.save(new Review(2L, 1L, 4, ""));
        reviewService.save(new Review(3L, 2L, 3, "Нормально"));

        System.out.println("=== Посетители ===");
        visitorService.findAll().forEach(System.out::println);

        System.out.println("=== Рестораны ===");
        restaurantService.findAll().forEach(System.out::println);

        System.out.println("=== Оценки ===");
        reviewService.findAll().forEach(System.out::println);
    }
}
