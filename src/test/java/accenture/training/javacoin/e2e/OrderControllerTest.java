package accenture.training.javacoin.e2e;

import accenture.training.javacoin.domain.Currencies;
import accenture.training.javacoin.domain.Order;
import accenture.training.javacoin.domain.User;
import accenture.training.javacoin.dtos.OrderResponse;
import accenture.training.javacoin.repository.OrderRepository;
import accenture.training.javacoin.repository.UserRepository;
import accenture.training.javacoin.valueObjects.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static accenture.training.javacoin.domain.Currencies.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.RequestEntity.get;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("http://localhost:${local.server.port}")
    private String localUrl;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void getAll() {
        String endpoint = "/orders";
        User user = new User(3);
        userRepository.save(user);
        orderRepository.save(Order.builder().selling(Money.of(5, JAVACOIN)).buying(Money.of(10, USD)).user(user).build());
        orderRepository.save(Order.builder().selling(Money.of(5, USD)).buying(Money.of(5000, ARS)).user(user).build());

        RequestEntity<Void> request = get(localUrl + endpoint).build();
        ResponseEntity<List<OrderResponse>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(OK);
        List<OrderResponse> orders = response.getBody();
        assertThat(orders).hasSize(2);

    }
}
