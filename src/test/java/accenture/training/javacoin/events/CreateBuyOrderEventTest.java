package accenture.training.javacoin.events;

import accenture.training.javacoin.domain.*;
import accenture.training.javacoin.dtos.CreateBuyOrderRequest;
import accenture.training.javacoin.repository.AccountRepository;
import accenture.training.javacoin.repository.OrderRepository;
import accenture.training.javacoin.repository.UserRepository;
import accenture.training.javacoin.valueObjects.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static accenture.training.javacoin.domain.Currencies.JAVACOIN;
import static accenture.training.javacoin.domain.Currencies.USD;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.post;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CreateBuyOrderEventTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("http://localhost:${local.server.port}")
    private String localUrl;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FeeCalculator feeCalculator;

    @Test
    @DisplayName("should create an order and update account")
    void createBuyOrderEvent() {
        String endpoint = "/orders";
        User user = new User(3);
        userRepository.save(user);
        Account javacoinAccount = new Account(user, "CUENTA DE AHORROS", Money.of(200, JAVACOIN), BigDecimal.ZERO);
        Account usdAccount = new Account(user, "CUENTA DE AHORROS", Money.of(200, USD), BigDecimal.ZERO);
        accountRepository.saveAll(List.of(javacoinAccount, usdAccount));
        CreateBuyOrderRequest orderRequest = new CreateBuyOrderRequest(user.getId(), Money.of(100, JAVACOIN), Money.of(50, USD));

        RequestEntity<CreateBuyOrderRequest> request = post(localUrl + endpoint).body(orderRequest);
        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        await().atMost(5, SECONDS).until(() -> orderRepository.findAll().size() == 1);
        Account userUSDAccount = accountRepository.findByUserId(user.getId()).get(1);
        assertThat(userUSDAccount.getMoney().getAmount()).isEqualTo(usdAccount.getMoney().getAmount().subtract(orderRequest.selling().getAmount().multiply(BigDecimal.valueOf(feeCalculator.calculate(user.getOperationsAmount())))));
        assertThat(userUSDAccount.getLockedAmount()).isEqualTo(orderRequest.selling().getAmount().multiply(BigDecimal.valueOf(feeCalculator.calculate(user.getOperationsAmount()))));
    }

    @Test
    void wtf() {
        User user = new User(3);
        userRepository.save(user);

        Account account2 = new Account(user, "CUENTA DE AHORROS", Money.of(200, USD), BigDecimal.ZERO);
        accountRepository.save(account2);

        Account accountDB = accountRepository.findByUserId(user.getId()).get(0);
        accountDB.setType("some other type");
        accountRepository.save(accountDB);

        assertThat(account2).isEqualTo(accountDB);
    }
}
