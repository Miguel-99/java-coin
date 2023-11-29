package accenture.training.javacoin.dtos;

import accenture.training.javacoin.valueObjects.Money;

public record CreateBuyOrderRequest(Long userId, Money buying, Money selling) {
    public CreateBuyOrderRequest {
        // Constructor lógico generado automáticamente por el record
    }

    @Override
    public String toString() {
        return "CreateBuyOrderRequest{" +
                "userId=" + userId +
                ", selling=" + selling +
                ", buying=" + buying +
                '}';
    }
}
