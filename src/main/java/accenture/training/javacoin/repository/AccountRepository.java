package accenture.training.javacoin.repository;

import accenture.training.javacoin.domain.Account;
import accenture.training.javacoin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);
}
