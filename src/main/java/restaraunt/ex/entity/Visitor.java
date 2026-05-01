package restaraunt.ex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visitor {

    private Long id;

    // Имя необязательное, так как отзыв можно оставить анонимно
    private String name;

    private int age;

    private String gender;
}
