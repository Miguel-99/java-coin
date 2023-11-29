package accenture.training.javacoin.domain;

import accenture.training.javacoin.valueObjects.Money;
import jakarta.persistence.*;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Builder
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="selling_amount")),
            @AttributeOverride(name="currency",column=@Column(name="selling_currency")),
    })
    private Money selling;

    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="buying_amount")),
            @AttributeOverride(name="currency",column=@Column(name="buying_currency")),
    })
    private Money buying;

    @Enumerated(EnumType.STRING)
    private OrderStatuses status;

    public Order() {}

    public Order(Long id, User user, Money selling, Money buying, OrderStatuses status) {
        this.id = id;
        this.user = user;
        this.selling = selling;
        this.buying = buying;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Money getSelling() {
        return selling;
    }

    public void setSelling(Money selling) {
        this.selling = selling;
    }

    public Money getBuying() {
        return buying;
    }

    public void setBuying(Money buying) {
        this.buying = buying;
    }

    public OrderStatuses getStatus() {
        return status;
    }

    public void setStatus(OrderStatuses status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
