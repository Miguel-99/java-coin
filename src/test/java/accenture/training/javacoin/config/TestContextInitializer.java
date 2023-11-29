package accenture.training.javacoin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

public class TestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(TestContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestContainers.start(applicationContext.getEnvironment());

        Map<String, String> testContainersProps = Map.of(
                "spring.rabbitmq.host", TestContainers.getRabbitMQHost(),
                "spring.rabbitmq.port", TestContainers.getRabbitMQPort(),
                "spring.rabbitmq.username", TestContainers.getRabbitMQUser(),
                "spring.rabbitmq.password", TestContainers.getRabbitMQPassword()
        );

        logger.info("test-containers props: {}", testContainersProps);
        TestPropertyValues values = TestPropertyValues.of(testContainersProps);
        values.applyTo(applicationContext);
    }
}
