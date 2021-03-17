package bsoft.com.clipboard.application.listener;

import bsoft.com.clipboard.storage.config.StorageConfig;
import bsoft.com.clipboard.storage.model.PostMessage;
import bsoft.com.clipboard.storage.repositories.PostMessageRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReaderTask implements Runnable {
    private final Channel channel;
    private final String exchangeName;
    private final String readerName;
    private final PostMessageRepository postMessageRepository;

    public ReaderTask(final Channel channel,
                      final String exchangeName,
                      final String readerName,
                      final StorageConfig storageConfig) {
        this.channel = channel;
        this.exchangeName = exchangeName;
        this.readerName = readerName;
        this.postMessageRepository = storageConfig.getPostMessageRepository();
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
                log.info("{} Receiver received topic:{} from {}:  {}", readerName, postMessage.getClipTopicName(), postMessage.getApiKey(), postMessage.getMessage());
                postMessageRepository.save(postMessage);
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
