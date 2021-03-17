package bsoft.com.clipboard.storage;


import bsoft.com.clipboard.model.ReaderContext;
import bsoft.com.clipboard.repositories.PostMessageRepository;
import bsoft.com.clipboard.repositories.ReaderContextRepository;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class ReaderTask implements Runnable {

    public PostMessageRepository postMessageRepository;
    public ReaderContextRepository readerContextRepository;

    public ReaderTask( final PostMessageRepository postMessageRepository) {
        this.postMessageRepository = postMessageRepository;
    }

    public void run() {
        log.info("{} started ReaderTask");

        String contextName = "reader";
        checkReaderContext(contextName);

        try {
            boolean goOn = true;

            while (goOn) {
                procesRecord(contextName);
            }
        } catch (Exception ex) {
            log.error("Error during processing: {}", ex);
        }
    }

    @Transactional
    public void procesRecord(final String contextName) {
        Long lastId;

        lastId = getLastId(contextName);
        if (lastId != -1L) {
            // get post message
            // process post message
            // update last message
        }

    }

    private Long getLastId(final String contextName) {
        Long lastId = -1L;

        Optional<ReaderContext> readerContextOptional = readerContextRepository.findByContextName(contextName);
        boolean found = true;

        if (readerContextOptional == null) {
            found = false;
        } else if (!readerContextOptional.isPresent()) {
            found = false;
        }

        if (found) {
            lastId = readerContextOptional.get().getLastId();
        }

        return lastId;
    }

    @Transactional
    public void checkReaderContext(final String name) {
        Optional<ReaderContext> readerContextOptional = readerContextRepository.findByContextName("name");

        boolean found = true;

        if (readerContextOptional == null) {
            found = false;
        } else if (!readerContextOptional.isPresent()) {
            found = false;
        }

        if (!found) {
            ReaderContext readerContext = new ReaderContext();
            readerContext.setContextName(name);
            readerContext.setLastId(0);
            readerContextRepository.save(readerContext);
        }
    }
}
