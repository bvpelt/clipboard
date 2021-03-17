package bsoft.com.clipboard.storage.config;



import bsoft.com.clipboard.storage.repositories.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Getter
@Setter
@NoArgsConstructor
public class StorageConfig {
    private PublisherRepository publisherRepository;

    private RegistrationTicketRepository registrationTicketRepository;

    private ClipTopicRepository clipTopicRepository;

    private SubscriptionRepository subscriptionRepository;

    private PostMessageRepository postMessageRepository;

    @Autowired
    public StorageConfig(final PublisherRepository publisherRepository,
                     final RegistrationTicketRepository registrationTicketRepository,
                     final ClipTopicRepository clipTopicRepository,
                     final SubscriptionRepository subscriptionRepository,
                         final PostMessageRepository postMessageRepository) {
        this.publisherRepository = publisherRepository;
        this.registrationTicketRepository = registrationTicketRepository;
        this.clipTopicRepository = clipTopicRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.postMessageRepository = postMessageRepository;
    }

    @Bean
    StorageConfig getStorageConfig() {
        return this;
    }
}

