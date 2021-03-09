package bsoft.com.clipboard.listener;

import bsoft.com.clipboard.config.ConfigElements;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Receiver {

    @Autowired
    private ConfigElements configElements;

    @Autowired
    private Channel channel;

    @Bean
    public Receiver newsReceiver(@Value("${news.reader}") final int maxReader) {
        ReaderTask[] readerTasks = new ReaderTask[maxReader];

        for (int i = 0; i < maxReader; i++) {
            readerTasks[i] = new ReaderTask(channel, configElements.getNewsExchanges(), "News");
            readerTasks[i].run();
        }
        return this;
    }

    @Bean
    public Receiver sportReceiver(@Value("${sport.reader}") final int maxReader) {
        ReaderTask[] readerTasks = new ReaderTask[maxReader];

        for (int i = 0; i < maxReader; i++) {
            readerTasks[i] = new ReaderTask(channel, configElements.getSportExchanges(), "Sport");
            readerTasks[i].run();
        }
        return this;
    }

    @Bean
    public Receiver financeReceiver(@Value("${finance.reader}") final int maxReader) {
        ReaderTask[] readerTasks = new ReaderTask[maxReader];

        for (int i = 0; i < maxReader; i++) {
            readerTasks[i] = new ReaderTask(channel, configElements.getFinanceExchanges(), "Finance");
            readerTasks[i].run();
        }
        return this;
    }

}
