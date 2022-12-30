package exercise;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("meal")
public class Meal {
    public String getMealForDaytime(String daytime) {

        switch (daytime) {
            case "morning":
                return "breakfast";
            case "day":
                return "lunch";
            case "evening":
                return "dinner";
            default:
                return "nothing :)";
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("Init bean meal");
    }
}
