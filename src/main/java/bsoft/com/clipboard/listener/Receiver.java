package bsoft.com.clipboard.listener;

import bsoft.com.clipboard.config.ConfigElements;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class Receiver {

    @Autowired
    private ConfigElements configElements;

    @Autowired
    private Channel channel;

    @Bean
    public Receiver newsReceiver() {

        try {
            channel.exchangeDeclare(configElements.getNewsExchanges(), "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, configElements.getNewsExchanges(), "");
            log.info("News Receiver - Waiting for messages");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                log.info("News Receiver received: {}", message);
            };
            boolean autoAck = true; // auto acknowledge
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            log.error("News Receiver - Problem creating queue: {}", e);
        }
        return this;
    }

    @Bean
    public Receiver sportReceiver() {

        try {
            channel.exchangeDeclare(configElements.getSportExchanges(), "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, configElements.getSportExchanges(), "");
            log.info("Sport Receiver - Waiting for messages");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                log.info("Sport Receiver received: {}", message);
            };
            boolean autoAck = true; // auto acknowledge
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            log.error("Sport Receiver - Problem creating queue: {}", e);
        }
        return this;
    }

    @Bean
    public Receiver financeReceiver() {

        try {
            channel.exchangeDeclare(configElements.getSportExchanges(), "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, configElements.getSportExchanges(), "");
            log.info("Finance Receiver - Waiting for messages");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                log.info("Finance Receiver received: {}", message);
            };
            boolean autoAck = true; // auto acknowledge
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            log.error("Finance Receiver - Problem creating queue: {}", e);
        }
        return this;
    }

}
