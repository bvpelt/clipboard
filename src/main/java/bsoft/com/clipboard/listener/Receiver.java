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

    private int maxReader = 50;

    @Autowired
    private ConfigElements configElements;

    @Autowired
    private Channel channel;

    @Bean
    public Receiver newsReceiver() {
        ReaderTask[] readerTasks = new ReaderTask[maxReader];;

        for (int i = 0; i < maxReader; i++) {
            readerTasks[i] = new ReaderTask(channel, configElements.getNewsExchanges(), "News");
            readerTasks[i].run();
        }
        return this;
    }

    @Bean
    public Receiver sportReceiver() {
        ReaderTask[] readerTasks = new ReaderTask[maxReader];;

        for (int i = 0; i < maxReader; i++) {
            readerTasks[i] = new ReaderTask(channel, configElements.getSportExchanges(), "Sport");
            readerTasks[i].run();
        }

        return this;
    }

    @Bean
    public Receiver financeReceiver() {
        ReaderTask[] readerTasks = new ReaderTask[maxReader];;

        for (int i = 0; i < maxReader; i++) {
            readerTasks[i] = new ReaderTask(channel, configElements.getFinanceExchanges(), "Finance");
            readerTasks[i].run();
        }

        return this;
    }

}
