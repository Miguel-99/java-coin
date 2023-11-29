package accenture.training.javacoin.domain;

import accenture.training.javacoin.valueObjects.Money;

import java.util.List;

public class Wallet {

    private List<Account> accounts;

    public Wallet() {}

    public Wallet(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public boolean contains(Money money) {
        return accounts.stream().anyMatch(account -> account.getMoney().getCurrency() == money.getCurrency() && account.getMoney().getAmount().compareTo(money.getAmount()) > 0);
    }
}
