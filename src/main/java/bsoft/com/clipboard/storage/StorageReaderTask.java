package bsoft.com.clipboard.storage;


import bsoft.com.clipboard.model.PostMessage;
import bsoft.com.clipboard.model.Publisher;
import bsoft.com.clipboard.model.ReaderContext;
import bsoft.com.clipboard.repositories.PostMessageRepository;
import bsoft.com.clipboard.repositories.PublisherRepository;
import bsoft.com.clipboard.repositories.ReaderContextRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Getter
@Setter
@Scope("prototype")
public class StorageReaderTask implements Runnable, Serializable {

    private final PostMessageRepository postMessageRepository;
    private final PublisherRepository publisherRepository;

    private final ReaderContextRepository readerContextRepository;

    private boolean goOn = true;
    private long interval = 10000L;
    private String name;
    private long msgProcessed = 0L;

    public StorageReaderTask(final PostMessageRepository postMessageRepository,
                             final ReaderContextRepository readerContextRepository,
                             final PublisherRepository publisherRepository) {
        this.postMessageRepository = postMessageRepository;
        this.readerContextRepository = readerContextRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run() {
        log.info("Started ReaderTask");
        String contextName = "reader";

        try {
            while (goOn) {
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
            log.info("Stopped processing - closing thread");
        } catch (Exception ex) {
            log.error("Error during processing: {}", ex);
        }
    }

    @Transactional
    public boolean procesRecord(final String contextName) {
        boolean recordFound = false;
        ReaderContext readerContext;

        readerContext = getLastId(contextName);
        if (readerContext != null) {
            // get post message
            List<PostMessage> postMessages = postMessageRepository.findNextContext(readerContext.getLastId());

            // process post message
            if ((postMessages != null) && (postMessages.size() == 1)) {
                PostMessage postMessage = postMessages.get(0);
                log.info("Processing id: {} in thread: {}", postMessage.getId(), name);

                List<Publisher> publishers = publisherRepository.findByApiKey(postMessage.getApiKey());

                if ((publishers != null) && (publishers.size() == 1)) {
                    String endpoint = publishers.get(0).getEndpoint();

                    try {
                        sendMessage(postMessage, endpoint);
                        readerContext.setLastId(readerContext.getLastId() + 1);
                        readerContextRepository.save(readerContext);
                        recordFound = true;
                        msgProcessed++;
                    } catch (JsonProcessingException e) {
                        log.error("Error in sending message: {}", e);
                    }
                } else {
                    log.error("No or more than one publisher found for api key: {}", postMessage.getApiKey());
                }
            }
        }
        return recordFound;
    }

    private ReaderContext getLastId(final String contextName) {
        ReaderContext lastContext = null;

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

    public void sendMessage(final PostMessage postMessage, final String endpoint) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "*/*");
        headers.add("Content-Type", "application/json");
        headers.add("Content-Type", "application/problem+json");
        headers.add("x-api-key", postMessage.getApiKey());

        HttpEntity request = new HttpEntity<>(new ObjectMapper().writeValueAsString(postMessage), headers);

        ResponseEntity<PostMessage> result = restTemplate.exchange(endpoint,
                HttpMethod.POST,
                request,
                PostMessage.class);

        log.info("Send message with result: {}", result.getStatusCode());
    }

}
