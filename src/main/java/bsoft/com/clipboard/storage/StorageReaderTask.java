package bsoft.com.clipboard.storage;


import bsoft.com.clipboard.model.PostMessage;
import bsoft.com.clipboard.model.ReaderContext;
import bsoft.com.clipboard.repositories.PostMessageRepository;
import bsoft.com.clipboard.repositories.ReaderContextRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rabbitmq.client.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Getter
@Setter
@Scope("prototype")
public class StorageReaderTask implements Runnable, Serializable {

    private final PostMessageRepository postMessageRepository;

    private final  ReaderContextRepository readerContextRepository;

    private boolean goOn = true;
    private long interval = 10000L;
    private String name;

    public StorageReaderTask(final PostMessageRepository postMessageRepository,
                             final ReaderContextRepository readerContextRepository) {
        this.postMessageRepository = postMessageRepository;
        this.readerContextRepository = readerContextRepository;
    }

    @Override
    public void run() {
        log.info("Started ReaderTask");
        String contextName = "reader";

        try {
            while(goOn) {
                log.info("Start processing");
                checkReaderContext(contextName);
                boolean recordFound = true;

                while (recordFound && goOn) {
                    try {
                        recordFound = procesRecord(contextName);
                    } catch (Exception e) {
                        log.error("Error processing message: {}", e);
                        goOn = false;
                    }
                }
                log.info("Start waiting");
                Thread.sleep(interval);
            }
            log.info("Stopped processing");
        } catch (Exception ex) {
            log.error("Error during processing: {}", ex);
        }
    }

    @Transactional
    public boolean procesRecord(final String contextName) {
        boolean goOn = false;
        ReaderContext readerContext;

        readerContext = getLastId(contextName);
        if (readerContext != null) {
            // get post message
            List<PostMessage> postMessages = postMessageRepository.findNextContext(readerContext.getLastId());

            // process post message
            if ((postMessages != null) && (postMessages.size() == 1)) {
                PostMessage postMessage = postMessages.get(0);
                log.info("Processing id: {}", postMessage.getId());

                readerContext.setLastId(readerContext.getLastId() + 1);
                readerContextRepository.save(readerContext);
                goOn = true;
            }
        }
        return goOn;
    }

    private ReaderContext getLastId(final String contextName) {
        ReaderContext lastContext= null;

        Optional<ReaderContext> readerContextOptional = readerContextRepository.findByContextName(contextName);
        boolean found = true;

        if (readerContextOptional == null) {
            found = false;
        } else if (!readerContextOptional.isPresent()) {
            found = false;
        }

        if (found) {
            lastContext = readerContextOptional.get();
            log.info("Lastid: {}", lastContext.getLastId());
        }

        return lastContext;
    }

    @Transactional
    public void checkReaderContext(final String name) {
        Optional<ReaderContext> readerContextOptional = readerContextRepository.findByContextName(name);

        boolean found = true;

        if (readerContextOptional == null) {
            found = false;
        } else if (!readerContextOptional.isPresent()) {
            found = false;
        }

        if (!found) {
            log.info("No reader context found, creating it");
            ReaderContext readerContext = new ReaderContext();
            readerContext.setContextName(name);
            readerContext.setLastId(0L);
            readerContextRepository.save(readerContext);
        }
    }
}
