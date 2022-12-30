package exercise.calculator;

import org.springframework.stereotype.Component;
import exercise.Inspect;

@Component
@Inspect(level = "info")
public class CalculatorImpl implements Calculator {
    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public int mult(int a, int b) {
        return a * b;
    }

    /*public int sum(int a, int b) {
        return a + b;
    }

    public int mult(int a, int b) {
        return a * b;
    }

     */
}
