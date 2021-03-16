package bsoft.com.clipboard.application.config;


import bsoft.com.clipboard.storage.repositories.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
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
    PublisherRepository publisherRepository() {
        return publisherRepository;
    }

    @Bean
    RegistrationTicketRepository registrationTicketRepository() {
        return registrationTicketRepository;
    }

    @Bean
    ClipTopicRepository clipTopicRepository() {
        return clipTopicRepository;
    }

    @Bean
    SubscriptionRepository subscriptionRepository() {
        return subscriptionRepository;
    }

    @Bean
    PostMessageRepository postMessageRepository() {
        return postMessageRepository;
    }
}
