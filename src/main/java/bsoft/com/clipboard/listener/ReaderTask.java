package bsoft.com.clipboard.listener;

import bsoft.com.clipboard.model.PostMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderTask implements Runnable {
    private final Channel channel;
    private final String exchangeName;
    private final String readerName;

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
                //String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                PostMessage postMessage = null;
                try {
                    postMessage = (PostMessage) PostMessage.byteToObj(delivery.getBody());
                } catch (ClassNotFoundException e) {
                    log.error("Could not convert data");
                }
                log.info("{} Receiver received topic:{} - {}", readerName, postMessage.getClipTopicName(), postMessage.getMessage());
            };
            boolean autoAck = true; // auto acknowledge
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            log.error("{}} Receiver - Problem creating queue: {}", readerName, e);
        }
        log.info("{} stopped", readerName);
    }
}
