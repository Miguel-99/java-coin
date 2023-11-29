package accenture.training.javacoin.domain;

import accenture.training.javacoin.repository.OrderRepository;
import accenture.training.javacoin.valueObjects.Money;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public class OrderBuilder {

    private User user;
    private Money selling;
    private Money buying;
    private OrderStatuses status;

    public OrderBuilder user(User user) {
        setUser(user);
        return this;
    }

    public OrderBuilder status(OrderStatuses status) {
        setStatus(status);
        return this;
    }

    public Order buildAndSaveIn(OrderRepository repository) {
        Order order = new Order();
        order.setUser(user);
        order.setSelling(selling);
        order.setBuying(buying);
        order.setStatus(status);
        return repository.save(order);
    }

    private void setUser(User user) {
        this.user = user;
    }

    private void setStatus(OrderStatuses status) {
        this.status = status;
    }

    public void setSelling(Money selling) {
        this.selling = selling;
    }

    public void setBuying(Money buying) {
        this.buying = buying;
    }
}
