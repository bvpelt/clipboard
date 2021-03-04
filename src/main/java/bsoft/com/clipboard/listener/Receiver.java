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
    public Receiver myReceiver() {

        try {
            String queueName = configElements.getQueueName();
            boolean durable = true;
            channel.queueDeclare(queueName, durable, false, false, null);
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);
            log.info("Receiver - Waiting for messages");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                log.info("Receiver received: {}", message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            boolean autoAck = false; // auto acknowledge
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            log.error("Receiver - Problem creating queue: {}", e);
        }
        return this;
    }

}
