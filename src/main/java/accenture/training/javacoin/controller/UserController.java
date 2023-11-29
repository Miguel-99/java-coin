package accenture.training.javacoin.controller;

import accenture.training.javacoin.domain.User;
import accenture.training.javacoin.service.UserService;
import accenture.training.javacoin.service.impl.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private RabbitMQProducer producer;

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    ResponseEntity<User> add(@RequestBody User user) {
        producer.sendMessage(user);
        return ResponseEntity.ok(userService.add(user));
    }
}
