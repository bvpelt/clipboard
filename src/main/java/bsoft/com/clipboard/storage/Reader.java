package bsoft.com.clipboard.storage;

import bsoft.com.clipboard.repositories.PostMessageRepository;
import bsoft.com.clipboard.repositories.ReaderContextRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Getter
@Setter
@Configuration
public class Reader {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PostMessageRepository postMessageRepository;
    @Autowired
    private ReaderContextRepository readerContextRepository;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private int maxReader = 3;
    private StorageReaderTask[] storageReaderTasks;
    private boolean executorStarted = false;

    @Bean
    public Reader getReader() {
        storageReaderTasks = new StorageReaderTask[maxReader];

        log.info("At startup - Thread pool executor - core poolsize: {}, max poolsize: {}, poolsize: {}, largest poolsize: {}", taskExecutor.getThreadPoolExecutor().getCorePoolSize(),
        taskExecutor.getThreadPoolExecutor().getMaximumPoolSize(),
        taskExecutor.getThreadPoolExecutor().getPoolSize(),
        taskExecutor.getThreadPoolExecutor().getLargestPoolSize());

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

        log.info("After start readers - Thread pool executor - core poolsize: {}, max poolsize: {}, poolsize: {}, largest poolsize: {}", taskExecutor.getThreadPoolExecutor().getCorePoolSize(),
                taskExecutor.getThreadPoolExecutor().getMaximumPoolSize(),
                taskExecutor.getThreadPoolExecutor().getPoolSize(),
                taskExecutor.getThreadPoolExecutor().getLargestPoolSize());

        return numberStarted;
    }

    public int stopReaders() {
        int numberStopped = 0;
        for (int i = 0; i < maxReader; i++) {
            if (storageReaderTasks[i] != null) {
                storageReaderTasks[i].setGoOn(false);
                numberStopped++;
            }
        }

        for (int i = 0; i < maxReader; i++) {
            if (storageReaderTasks[i] != null) {
                taskExecutor.getThreadPoolExecutor().remove(storageReaderTasks[i]);
                storageReaderTasks[i] = null;
            }
        }

        taskExecutor.getThreadPoolExecutor().purge();
        taskExecutor.

        log.info("After stop readers - Thread pool executor - core poolsize: {}, max poolsize: {}, poolsize: {}, largest poolsize: {}", taskExecutor.getThreadPoolExecutor().getCorePoolSize(),
                taskExecutor.getThreadPoolExecutor().getMaximumPoolSize(),
                taskExecutor.getThreadPoolExecutor().getPoolSize(),
                taskExecutor.getThreadPoolExecutor().getLargestPoolSize());

        return numberStopped;
    }
}
