package com.bsoft.clipboard.service;


import com.bsoft.clipboard.persist.model.ClipTopic;
import com.bsoft.clipboard.persist.model.RegistrationTicket;
import com.bsoft.clipboard.persist.model.TopicTicket;
import com.bsoft.clipboard.persist.model.User;
import com.bsoft.clipboard.persist.repositories.RegistrationTicketRepository;
import com.bsoft.clipboard.persist.repositories.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public Clipboard(final UserRepository userRepository,
                     final RegistrationTicketRepository registrationTicketRepository) {
        this.userRepository = userRepository;
        this.registrationTicketRepository = registrationTicketRepository;
    }

    @Transactional
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
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

    public TopicTicket defineClipTopic(final ClipTopic clipTopic) {
        TopicTicket topicTicket = null;

        return topicTicket;
    }
}
