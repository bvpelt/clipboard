package bsoft.com.clipboard.model;


import bsoft.com.clipboard.controller.BadParameterException;
import bsoft.com.clipboard.controller.PublisherNotFoundException;
import bsoft.com.clipboard.repositories.ClipTopicRepository;
import bsoft.com.clipboard.repositories.PublisherRepository;
import bsoft.com.clipboard.repositories.RegistrationTicketRepository;
import bsoft.com.clipboard.repositories.SubscriptionRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Getter
@Setter
@NoArgsConstructor
public class Clipboard {

    private PublisherRepository publisherRepository;
    private RegistrationTicketRepository registrationTicketRepository;
    private ClipTopicRepository clipTopicRepository;
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public Clipboard(final PublisherRepository publisherRepository,
                     final RegistrationTicketRepository registrationTicketRepository,
                     final ClipTopicRepository clipTopicRepository,
                     final SubscriptionRepository subscriptionRepository) {
        this.publisherRepository = publisherRepository;
        this.registrationTicketRepository = registrationTicketRepository;
        this.clipTopicRepository = clipTopicRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public List<Publisher> getPublishers() {
        List<Publisher> publishers = publisherRepository.findAll(Sort.by("email"));

        return publishers;
    }

    @Transactional
    public Optional<Publisher> getPublisherById(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        return publisher;
    }

    @Transactional
    public List<Publisher> getPublisherFromApiKey(final String apiKey) {
        List<Publisher> publisher = publisherRepository.findByApiKey(apiKey);

        return publisher;
    }

    @Transactional
    public void deletePublisherById(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);

        if ((publisher != null) && publisher.isPresent()) {
            RegistrationTicket registrationTicket = publisher.get().getRegistrationTicket();
            registrationTicket.setStatus("inactive");
            registrationTicketRepository.save(registrationTicket);
            publisherRepository.deleteById(id);
        } else {
            throw new BadParameterException("Publisher not found by id");
        }
    }

    @Transactional
    public Optional<Publisher> confirmPublisher(Long publisherId) {
        Optional<Publisher> publisher = updatePublisherStatus(publisherId, "confirmed");
        return publisher;
    }

    @Transactional
    public Optional<Publisher> disablePublisher(Long publisherId) {
        Optional<Publisher> publisher = updatePublisherStatus(publisherId, "disabled");
        return publisher;
    }

    @Transactional
    public Optional<Publisher> removePublisher(Long publisherId) {
        Optional<Publisher> publisher = updatePublisherStatus(publisherId, "removed");
        return publisher;
    }

    private Optional<Publisher> updatePublisherStatus(Long publisherId, final String status) {
        Optional<Publisher> publisher = publisherRepository.findById(publisherId);
        if (publisher.isPresent()) {
            Publisher publisher1 = publisher.get();
            publisher1.setStatus(status);
            publisherRepository.save(publisher1);
        }
        return publisher;
    }

    @Transactional
    public Publisher registerPublisher(final Publisher publisher) {
        RegistrationTicket registrationTicket = new RegistrationTicket();

        // Check if
        // - the publisher exists by checking email adres
        // if (publisher exists)
        // - return error message - publisher already exists
        // else
        // - return publisher

        Optional<Publisher> optionalPublisher = publisherRepository.findByEmail(publisher.getEmail());

        if ((optionalPublisher != null) && optionalPublisher.isPresent()) {
            throw new BadParameterException(("Publisher already exists"));
        } else {
            String newPublisherTicket = UUID.randomUUID().toString();
            registrationTicket.setStatus("created");
            registrationTicket.setPublisherTicket(newPublisherTicket);

            registrationTicketRepository.save(registrationTicket);

            publisher.setStatus("create");
            publisher.setRegistrationTicket(registrationTicket);
            publisherRepository.save(publisher);
        }
        return publisher;
    }

    public ClipTopic registerClipTopic(final ClipTopic clipTopic) {
        ClipTopic registeredClipTopic;
        // Check if
        // - the cliptopic exists by checking name
        // if (cliptopic exists)
        // - return error messages - cliptopic exists
        // else
        // - return cliptopic

        Optional<ClipTopic> optionalClipTopic = clipTopicRepository.findByName(clipTopic.getName());
        if ((optionalClipTopic != null) && optionalClipTopic.isPresent()) {
            throw new BadParameterException("cliptopic already exists");
        } else {

            registeredClipTopic = clipTopicRepository.save(clipTopic);
        }
        return registeredClipTopic;
    }

    @Transactional
    public List<ClipTopic> getClipTopics() {
        List<ClipTopic> clipTopics = clipTopicRepository.findAll(Sort.by("name"));

        return clipTopics;
    }

    @Transactional
    public Optional<ClipTopic> getClipTopicById(Long id) {
        Optional<ClipTopic> clipTopic = clipTopicRepository.findById(id);
        return clipTopic;
    }

    @Transactional
    public Optional<ClipTopic> getClipTopicByName(String name) {
        Optional<ClipTopic> clipTopic = clipTopicRepository.findByName(name);
        return clipTopic;
    }

    @Transactional
    public Optional<Publisher> addPublisherSubscriptions(Long id, String[] names) {
        Optional<Publisher> publisher = publisherRepository.findById(id);

        if ((publisher != null) && publisher.isPresent()) {
            Publisher curPublisher = publisher.get();
            for (int i = 0; i < names.length; i++) {
                Optional<ClipTopic> clipTopic = clipTopicRepository.findByName(names[i]);
                if ((clipTopic != null) && clipTopic.isPresent()) {
                    ClipTopic curClipTopic = clipTopic.get();
                    Optional<Subscription> subscription = subscriptionRepository.findByPublisherAndClipTopic(curPublisher, curClipTopic);
                    if (!((subscription != null) && subscription.isPresent())) {
                        Subscription newSubscription = new Subscription();
                        newSubscription.setPublisher(curPublisher);
                        newSubscription.setClipTopic(curClipTopic);
                        subscriptionRepository.save(newSubscription);
                    } else {
                        log.info("Subscription already exists, not added again");
                    }
                } else {
                    log.info("ClipTopic: {} not found, not added to subscription", names[i]);
                }
            }
        } else {
            throw new PublisherNotFoundException("Publisher not found");
        }
        return publisher;
    }

    @Transactional
    public List<Subscription> getSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        return subscriptions;
    }

    @Transactional
    public boolean checkSubscription(Publisher publisher, ClipTopic clipTopic) {
        boolean valid = true;
        List<Subscription> subscription = subscriptionRepository.findByPublisherAndClipTopicName(publisher.getId(), clipTopic.getName());

        if ((subscription == null) || (subscription.size() != 1)) {
            valid = false;
        }

        return valid;
    }
}
