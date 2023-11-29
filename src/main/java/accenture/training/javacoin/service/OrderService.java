package accenture.training.javacoin.service;

import accenture.training.javacoin.domain.Order;
import accenture.training.javacoin.dtos.CreateBuyOrderRequest;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    Order create(CreateBuyOrderRequest orderRequest);
}
