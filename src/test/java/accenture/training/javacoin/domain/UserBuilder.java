package accenture.training.javacoin.domain;

import accenture.training.javacoin.repository.UserRepository;

public class UserBuilder {

    private Wallet wallet;

    private Integer operationsAmount;

    public UserBuilder wallet(Wallet wallet) {
        setWallet(wallet);
        return this;
    }

    public UserBuilder operationsAmount(Integer operationsAmount) {
        setOperationsAmount(operationsAmount);
        return this;
    }

    public User buildAndSaveIn(UserRepository repository) {
        User user = new User();
        user.setWallet(wallet);
        user.setOperationsAmount(operationsAmount);
        return repository.save(user);
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Integer getOperationsAmount() {
        return operationsAmount;
    }

    public void setOperationsAmount(Integer operationsAmount) {
        this.operationsAmount = operationsAmount;
    }
}
