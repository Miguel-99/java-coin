package accenture.training.javacoin.service.impl;

import accenture.training.javacoin.domain.User;
import accenture.training.javacoin.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Value("${exchange.name}")
    private String exchange;

    @Value("${routingKey.name}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Object message) {
        logger.info("Message sent -> {}", message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
