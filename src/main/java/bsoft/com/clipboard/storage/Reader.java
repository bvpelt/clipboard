package bsoft.com.clipboard.storage;

import bsoft.com.clipboard.repositories.PostMessageRepository;
import bsoft.com.clipboard.repositories.ReaderContextRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Getter
@Setter
@Configuration
public class Reader {

    private  int maxReader = 1;
    private StorageReaderTask[] storageReaderTasks;

    @Autowired
    private PostMessageRepository postMessageRepository;

    @Autowired
    private ReaderContextRepository readerContextRepository;

    @Bean
    public Reader getReader() {
        storageReaderTasks = new StorageReaderTask[maxReader];
        for (int i = 0; i < maxReader; i++) {
            storageReaderTasks[i] = new StorageReaderTask(postMessageRepository, readerContextRepository);
            //storageReaderTasks[i].run();
        }

        return this;
    }

    public int startReaders() {
        int numberStarted = 0;

        for (int i = 0; i < maxReader; i++) {
            storageReaderTasks[i].run();
            storageReaderTasks[i].setGoOn(true);
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
        return numberStopped;
    }
}
