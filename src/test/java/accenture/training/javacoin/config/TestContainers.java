package accenture.training.javacoin.config;

import org.springframework.core.env.Environment;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainers {
    private static final RabbitMQContainer RABBITMQ_CONTAINER = new RabbitMQContainer(
            DockerImageName.parse("rabbitmq:3.12.8-alpine")
    );

    public static void start(Environment env) {
        RABBITMQ_CONTAINER.start();
    }

    public static String getRabbitMQHost() {
        return RABBITMQ_CONTAINER.getHost();
    }

    public static String getRabbitMQPort() {
        return RABBITMQ_CONTAINER.getAmqpPort().toString();
    }

    public static String getRabbitMQUser() {
        return RABBITMQ_CONTAINER.getAdminUsername();
    }

    public static String getRabbitMQPassword() {
        return RABBITMQ_CONTAINER.getAdminPassword();
    }
}
