package bsoft.com.clipboard.model;

import bsoft.com.clipboard.repositories.ClipTopicRepository;
import bsoft.com.clipboard.repositories.RegistrationTicketRepository;
import bsoft.com.clipboard.repositories.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Getter
@Setter
@NoArgsConstructor
public class Clipboard {

    private UserRepository userRepository;
    private RegistrationTicketRepository registrationTicketRepository;
    private ClipTopicRepository clipTopicRepository;

    @Autowired
    public Clipboard(final UserRepository userRepository,
                     final RegistrationTicketRepository registrationTicketRepository,
                     final ClipTopicRepository clipTopicRepository) {
        this.userRepository = userRepository;
        this.registrationTicketRepository = registrationTicketRepository;
        this.clipTopicRepository = clipTopicRepository;
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
    public RegistrationTicket registerUser(final User user) {
        RegistrationTicket registrationTicket = new RegistrationTicket();

        // Check if
        // - the user exists by checking email adres
        // if (user exists)
        // - return error message - registration already done
        // else
        // - return registration ticket


        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if ((optionalUser != null) && optionalUser.isPresent()) {
            // return error
            registrationTicket.setStatus("error");
            registrationTicket.setErrorMessage("User already exists");
        } else {
            String newUserTicket = UUID.randomUUID().toString();
            registrationTicket.setStatus("ok");
            registrationTicket.setUserTicket(newUserTicket);

            registrationTicketRepository.save(registrationTicket);

            user.setStatus("create");
            user.setRegistrationTicket(registrationTicket);
            userRepository.save(user);
        }
        return registrationTicket;
    }

    public ClipTopic registerClipTopic(final ClipTopic clipTopic) {

        ClipTopic registeredClipTopic;
        registeredClipTopic = clipTopicRepository.save(clipTopic);
        return registeredClipTopic;
    }
}
