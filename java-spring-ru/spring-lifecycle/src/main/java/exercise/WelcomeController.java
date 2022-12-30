package exercise;

import exercise.daytimes.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @Autowired
    private Daytime daytime;

    @Autowired
    private Meal meal;

    @GetMapping(path = "/daytime")
    public String getText() {
        String daytimeName = daytime.getName();
        return "It is "+ daytimeName + " now. Enjoy your " + meal.getMealForDaytime(daytimeName);
    }
}
