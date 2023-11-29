package accenture.training.javacoin.controller;

import accenture.training.javacoin.domain.Order;
import accenture.training.javacoin.dtos.CreateBuyOrderRequest;
import accenture.training.javacoin.dtos.OrderResponse;
import accenture.training.javacoin.service.OrderService;
import accenture.training.javacoin.service.impl.RabbitMQProducer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private RabbitMQProducer producer;

    @Autowired
    private OrderService orderService;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    ResponseEntity<List<OrderResponse>> orderList() {
        return ResponseEntity.ok(this.toOrderResponse(orderService.getAll()));
    }

    @PostMapping
    ResponseEntity<Void> create(@RequestBody CreateBuyOrderRequest orderRequest) {
        producer.sendMessage(orderRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private List<OrderResponse> toOrderResponse(List<Order> orders) {
        return orders.stream().map(this::toOrderResponse).toList();
    }

    private OrderResponse toOrderResponse(Order order) {
        return mapper.map(order, OrderResponse.class);
    }
}
