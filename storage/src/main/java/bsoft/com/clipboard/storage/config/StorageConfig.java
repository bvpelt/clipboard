package bsoft.com.clipboard.storage.config;


import bsoft.com.clipboard.storage.repositories.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class StorageConfig {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private RegistrationTicketRepository registrationTicketRepository;

    @Autowired
    private ClipTopicRepository clipTopicRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PostMessageRepository postMessageRepository;


    @Bean
    public PublisherRepository publisherRepository() {
        return publisherRepository;
    }

    @Bean
    public RegistrationTicketRepository registrationTicketRepository() {
        return registrationTicketRepository;
    }

    @Bean
    public ClipTopicRepository clipTopicRepository() {
        return clipTopicRepository;
    }

    @Bean
    public SubscriptionRepository subscriptionRepository() {
        return subscriptionRepository;
    }

    @Bean
    public PostMessageRepository postMessageRepository() {
        return postMessageRepository;
    }

}

