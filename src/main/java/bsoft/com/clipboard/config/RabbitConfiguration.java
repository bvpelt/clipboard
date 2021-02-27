package bsoft.com.clipboard.config;

import lombok.Getter;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
