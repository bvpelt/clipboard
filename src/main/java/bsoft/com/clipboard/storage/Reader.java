package bsoft.com.clipboard.storage;

import bsoft.com.clipboard.repositories.PostMessageRepository;
import bsoft.com.clipboard.repositories.ReaderContextRepository;
import liquibase.pro.packaged.A;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Getter
@Setter
@Configuration
public class Reader {

    private int maxReader = 1;
    private StorageReaderTask[] storageReaderTasks;
    private boolean executorStarted = false;

    @Autowired
    private PostMessageRepository postMessageRepository;

    @Autowired
    private ReaderContextRepository readerContextRepository;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public Reader getReader() {

        storageReaderTasks = new StorageReaderTask[maxReader];

        return this;
    }

    public int startReaders() {
        int numberStarted = 0;
        String name;

        for (int i = 0; i < maxReader; i++) {
            name = "StorageReaderTask_" + i;

            if (storageReaderTasks[i] == null) {
                storageReaderTasks[i] = (StorageReaderTask) applicationContext.getBean("storageReaderTask");
                storageReaderTasks[i].setName(name);
            }
            storageReaderTasks[i].setGoOn(true);
            taskExecutor.execute(storageReaderTasks[i]);
            executorStarted = true;

            numberStarted++;
        }

        return numberStarted;
    }

    public int stopReaders() {
        int numberStopped = 0;
        for (int i = 0; i < maxReader; i++) {
            storageReaderTasks[i].setGoOn(false);
            numberStopped++;
        }

        for (int i = 0; i < maxReader; i++) {
            storageReaderTasks[i] = null;
        }

        return numberStopped;
    }
}
