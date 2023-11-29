package accenture.training.javacoin.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeeCalculator {

    @Value("${fee.tier1.amount}")
    private double tier1Fee;
    @Value("${fee.tier2.amount}")
    private double tier2Fee;
    @Value("${fee.tier3.amount}")
    private double tier3Fee;

    public Double calculate(Integer operationsAmount) {
        if (operationsAmount < 3) {
            return tier1Fee;
        } else if (operationsAmount < 7) {
            return tier2Fee;
        } else {
            return tier3Fee;
        }
    }
}
