package bsoft.com.clipboard.storage;

import bsoft.com.clipboard.repositories.PostMessageRepository;
import bsoft.com.clipboard.repositories.ReaderContextRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Reader {

    @Autowired
    private PostMessageRepository postMessageRepository;

    @Autowired
    private ReaderContextRepository readerContextRepository;

    @Bean
    public Reader getReader() {

        StorageReaderTask[] storageReaderTasks = new StorageReaderTask[1];
        int maxReader = storageReaderTasks.length;
        for (int i = 0; i < maxReader; i++) {
            storageReaderTasks[i] = new StorageReaderTask(postMessageRepository, readerContextRepository);
            storageReaderTasks[i].run();
        }

        return this;
    }
}
