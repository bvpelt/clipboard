package bsoft.com.clipboard.listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ReaderTask implements Runnable {
    private Channel channel;
    private String exchangeName;
    private String readerName;

    public ReaderTask(final Channel channel,
                      final String exchangeName,
                      final String readerName) {
        this.channel = channel;
        this.exchangeName = exchangeName;
        this.readerName = readerName;
    }

    public void run() {
        log.info("{} started", readerName);
        try {
            channel.exchangeDeclare(exchangeName, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, exchangeName, "");
            log.info("{}} Receiver - Waiting for messages", readerName);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                log.info("{} Receiver received: {}", readerName, message);
            };
            boolean autoAck = true; // auto acknowledge
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            log.error("{}} Receiver - Problem creating queue: {}", readerName, e);
        }
        log.info("{} stopped", readerName);
    }
}
