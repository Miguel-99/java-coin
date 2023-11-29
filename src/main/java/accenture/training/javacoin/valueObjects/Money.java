package accenture.training.javacoin.valueObjects;

import accenture.training.javacoin.domain.Currencies;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Embeddable
public class Money {
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Currencies currency;

    public Money() {
    }

    public Money(Integer amount, Currencies currency) {
        this.amount = BigDecimal.valueOf(amount);
        this.currency = currency;
    }

    public Money(Double amount, Currencies currency) {
        this.amount = BigDecimal.valueOf(amount);
        this.currency = currency;
    }

    public Money(BigDecimal amount, Currencies currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(BigDecimal amount, Currencies currency) {
        return new Money(amount, currency);
    }

    public static Money of(Integer amount, Currencies currency) {
        return new Money(amount, currency);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currencies getCurrency() {
        return currency;
    }

    public void setCurrency(Currencies currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
