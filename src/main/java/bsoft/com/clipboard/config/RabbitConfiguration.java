package bsoft.com.clipboard.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Configuration
@Getter
public class RabbitConfiguration {
    private final String hostName = "localhost";
    private final String queueName = "topics";
    private final String fanoutExchange = "fanoutExchange";

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(hostName);
    }

    @Bean
    public Channel channel(CachingConnectionFactory connectionFactory) {
        Channel channel = null;
        try {
            Connection connection = connectionFactory.getRabbitConnectionFactory().newConnection();
            channel = connection.createChannel();
        } catch (TimeoutException ex1) {
            log.error("Timeout: {}", ex1);
        } catch (IOException ex2) {
            log.error("IO exception: {}", ex2);
        } catch (Exception ex3) {
            log.error("Unknown exception: {}", ex3);
        }
        log.info("Channel created");
        return channel;
    }

    @Bean
    public ConfigElements configElements() {
        return new ConfigElements();
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue financeQueue() {
        return new Queue("finance");
    }

    @Bean
    public Queue newsQueue() {
        return new Queue("news");
    }

    @Bean
    public Queue sportQueue() {
        return new Queue("sport");
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(fanoutExchange);
    }

    @Bean
    public Binding financeBinding(Queue financeQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(financeQueue).to(exchange);
    }

    @Bean
    public Binding newsBinding(Queue newsQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(newsQueue).to(exchange);
    }

    @Bean
    public Binding sportBinding(Queue sportQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(sportQueue).to(exchange);
    }

    @Bean
    public String exchangeName() {
        return fanoutExchange;
    }
/*
    @Bean
    public Queue topicQueue() {
        return new Queue(queueName);
    }

    @Bean
    public String rabbitHostName() {
        return hostName;
    }

    @Bean
    public String rabbitQueueName() {
        return queueName;
    }

 */
}
