package accenture.training.javacoin.repository;

import accenture.training.javacoin.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
