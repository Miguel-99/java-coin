package accenture.training.javacoin.domain;

import accenture.training.javacoin.valueObjects.Money;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Wallet wallet;

    private Integer operationsAmount;

    public User() {}

    public User(Long id, Wallet wallet, Integer operationsAmount) {
        this.id = id;
        this.wallet = wallet;
        this.operationsAmount = operationsAmount;
    }

    public User(Long id, Integer operationsAmount) {
        this.id = id;
        this.operationsAmount = operationsAmount;
    }

    public User(Integer operationsAmount) {
        this.operationsAmount = operationsAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean has(Money money) {
        return wallet.contains(money);
    }

    public Integer getOperationsAmount() {
        return operationsAmount;
    }

    public void setOperationsAmount(Integer operationsAmount) {
        this.operationsAmount = operationsAmount;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }
}
