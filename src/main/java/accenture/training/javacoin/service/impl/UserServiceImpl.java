package accenture.training.javacoin.service.impl;

import accenture.training.javacoin.domain.Account;
import accenture.training.javacoin.domain.User;
import accenture.training.javacoin.repository.AccountRepository;
import accenture.training.javacoin.repository.UserRepository;
import accenture.training.javacoin.service.UserService;
import accenture.training.javacoin.valueObjects.Money;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public void lock(Long userId, Money money) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        Account account = accounts.stream().filter(a -> a.getMoney().getCurrency() == money.getCurrency()).findFirst().orElseThrow();
        account.setLockedAmount(money.getAmount());
        account.getMoney().setAmount(account.getMoney().getAmount().subtract(money.getAmount()));
        accountRepository.save(account);
    }
}
