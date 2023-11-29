package accenture.training.javacoin.repository;

import accenture.training.javacoin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
