package bsoft.com.clipboard.model;

import bsoft.com.clipboard.controller.BadParameterException;
import bsoft.com.clipboard.controller.UserNotFoundException;
import bsoft.com.clipboard.repositories.ClipTopicRepository;
import bsoft.com.clipboard.repositories.RegistrationTicketRepository;
import bsoft.com.clipboard.repositories.SubscriptionRepository;
import bsoft.com.clipboard.repositories.UserRepository;
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

    private UserRepository userRepository;
    private RegistrationTicketRepository registrationTicketRepository;
    private ClipTopicRepository clipTopicRepository;
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public Clipboard(final UserRepository userRepository,
                     final RegistrationTicketRepository registrationTicketRepository,
                     final ClipTopicRepository clipTopicRepository,
                     final SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.registrationTicketRepository = registrationTicketRepository;
        this.clipTopicRepository = clipTopicRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public List<User> getUsers() {
        List<User> users = userRepository.findAll(Sort.by("email"));

        return users;
    }

    @Transactional
    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @Transactional
    public List<User> getUserFromApiKey(final String apiKey) {
        List<User> user = userRepository.findByApiKey(apiKey);

        return user;
    }

    @Transactional
    public void deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if ((user != null) && user.isPresent()) {
            RegistrationTicket registrationTicket = user.get().getRegistrationTicket();
            registrationTicket.setStatus("inactive");
            registrationTicketRepository.save(registrationTicket);
            userRepository.deleteById(id);
        } else {
            throw new BadParameterException("User not found by id");
        }
    }

    @Transactional
    public Optional<User> confirmUser(Long userId) {
        Optional<User> user = updateUserStatus(userId, "confirmed");
        return user;
    }

    @Transactional
    public Optional<User> disableUser(Long userId) {
        Optional<User> user = updateUserStatus(userId, "disabled");
        return user;
    }

    @Transactional
    public Optional<User> removeUser(Long userId) {
        Optional<User> user = updateUserStatus(userId, "removed");
        return user;
    }

    private Optional<User> updateUserStatus(Long userId, final String status) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setStatus(status);
            userRepository.save(user1);
        }
        return user;
    }

    @Transactional
    public User registerUser(final User user) {
        RegistrationTicket registrationTicket = new RegistrationTicket();

        // Check if
        // - the user exists by checking email adres
        // if (user exists)
        // - return error message - user already exists
        // else
        // - return user

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if ((optionalUser != null) && optionalUser.isPresent()) {
            throw new BadParameterException(("user already exists"));
        } else {
            String newUserTicket = UUID.randomUUID().toString();
            registrationTicket.setStatus("created");
            registrationTicket.setUserTicket(newUserTicket);

            registrationTicketRepository.save(registrationTicket);

            user.setStatus("create");
            user.setRegistrationTicket(registrationTicket);
            userRepository.save(user);
        }
        return user;
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
    public Optional<User> addUserSubscriptions(Long id, String[] names) {
        Optional<User> user = userRepository.findById(id);

        if ((user != null) && user.isPresent()) {
            User curUser = user.get();
            for (int i = 0; i < names.length; i++) {
                Optional<ClipTopic> clipTopic = clipTopicRepository.findByName(names[i]);
                if ((clipTopic != null) && clipTopic.isPresent()) {
                    ClipTopic curClipTopic = clipTopic.get();
                    Optional<Subscription> subscription = subscriptionRepository.findByUserAndClipTopic(curUser, curClipTopic);
                    if (!((subscription != null) && subscription.isPresent())) {
                        Subscription newSubscription = new Subscription();
                        newSubscription.setUser(curUser);
                        newSubscription.setClipTopic(curClipTopic);

                        /*
                        curUser.getSubscriptions().add(newSubscription);
                        curClipTopic.getSubscriptions().add(newSubscription);

                        userRepository.save(curUser);



                        clipTopicRepository.save(curClipTopic);

                         */
                        subscriptionRepository.save(newSubscription);

                    } else {
                        log.info("Subscription already exists, not added again");
                    }
                } else {
                    log.info("ClipTopic: {} not found, not added to subscription", names[i]);
                }
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Transactional
    public List<Subscription> getSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        return subscriptions;
    }

    @Transactional
    public boolean checkSubscription(User user, ClipTopic clipTopic) {
        boolean valid = true;
        List<Subscription> subscription = subscriptionRepository.findByUserAndClipTopicName(user.getId(), clipTopic.getName());

        if ((subscription == null) || (subscription.size() != 1)) {
            valid = false;
        }

        return valid;
    }
}
