package accenture.training.javacoin.service;

import accenture.training.javacoin.domain.User;
import accenture.training.javacoin.valueObjects.Money;

import java.math.BigDecimal;

public interface UserService {
    User add(User user);
    User getById(Long userId);
    void lock(Long userId, Money money);
}
