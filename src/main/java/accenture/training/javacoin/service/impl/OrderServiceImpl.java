package accenture.training.javacoin.service.impl;

import accenture.training.javacoin.domain.Order;
import accenture.training.javacoin.domain.OrderStatuses;
import accenture.training.javacoin.domain.User;
import accenture.training.javacoin.dtos.CreateBuyOrderRequest;
import accenture.training.javacoin.repository.OrderRepository;
import accenture.training.javacoin.repository.UserRepository;
import accenture.training.javacoin.service.OrderService;
import accenture.training.javacoin.valueObjects.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order create(CreateBuyOrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.userId())
                .orElseThrow(() -> new RuntimeException("User with id: " + orderRequest.userId() + " not found"));
        Order order = new Order();
        order.setUser(user);
        order.setBuying(orderRequest.buying());
        order.setSelling(orderRequest.selling());
        order.setStatus(OrderStatuses.PENDING);
        return orderRepository.save(order);
    }
}
