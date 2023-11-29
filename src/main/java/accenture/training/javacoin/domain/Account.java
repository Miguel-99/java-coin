package accenture.training.javacoin.domain;

import accenture.training.javacoin.valueObjects.Money;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String type;

    private Money money;

    private BigDecimal lockedAmount;

    public Account() {}

    public Account(User user, String type, Money money, BigDecimal lockedAmount) {
        this.user = user;
        this.type = type;
        this.money = money;
        this.lockedAmount = lockedAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
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

    public BigDecimal getLockedAmount() {
        return lockedAmount;
    }

    public void setLockedAmount(BigDecimal lockedAmount) {
        this.lockedAmount = lockedAmount;
    }

}
