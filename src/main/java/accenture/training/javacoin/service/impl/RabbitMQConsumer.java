package accenture.training.javacoin.service.impl;

import accenture.training.javacoin.domain.FeeCalculator;
import accenture.training.javacoin.domain.User;
import accenture.training.javacoin.domain.Wallet;
import accenture.training.javacoin.dtos.CreateBuyOrderRequest;
import accenture.training.javacoin.repository.AccountRepository;
import accenture.training.javacoin.service.OrderService;
import accenture.training.javacoin.service.UserService;
import accenture.training.javacoin.valueObjects.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static accenture.training.javacoin.domain.Currencies.JAVACOIN;

@Service
public class RabbitMQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Value("${fee.tier1.amount}")
    private double tier1Fee;

    @Value("${fee.tier2.amount}")
    private double tier2Fee;

    @Value("${fee.tier3.amount}")
    private double tier3Fee;

    @Autowired
    private MessageConverter converter;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FeeCalculator feeCalculator;

    @RabbitListener(queues = {"${queue.name}"})
    public void consume(Message message) {
        Object object = converter.fromMessage(message);
        if (object instanceof CreateBuyOrderRequest buyReq) {
            logger.info("received CreateBuyOrderRequest: {}", object);
            User user = userService.getById(buyReq.userId());
            user.setWallet(new Wallet(accountRepository.findByUserId(user.getId())));
            if (user.has(Money.of(buyReq.buying().getAmount().multiply(BigDecimal.valueOf(feeCalculator.calculate(user.getOperationsAmount()))), JAVACOIN))) {
                logger.info("descontando del usuario {} un saldo de {} {} en su cuenta", user.getId(), buyReq.selling().getAmount().multiply(BigDecimal.valueOf(feeCalculator.calculate(user.getOperationsAmount()))), buyReq.buying().getCurrency());
                userService.lock(buyReq.userId(), Money.of(buyReq.selling().getAmount().multiply(BigDecimal.valueOf(feeCalculator.calculate(user.getOperationsAmount()))), buyReq.selling().getCurrency()));
                orderService.create(buyReq);
                return;
            }
            logger.info("el usuario {} no tiene {} {} en su cuenta", user.getId(), buyReq.selling().getAmount().multiply(BigDecimal.valueOf(feeCalculator.calculate(user.getOperationsAmount()))), buyReq.selling().getCurrency());
            return;
        }

        logger.info("received: {}", object);
    }
}
