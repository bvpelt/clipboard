package bsoft.com.clipboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Clipboard {

    public RegistrationTicket registerUser(final User user) {
        RegistrationTicket registrationTicket = null;

        String newUserTicket = UUID.randomUUID().toString();

        // Check if
        // - the user exists by checking email adres
        // if (user exists)
        // - return error message - registration already done
        // else
        // - return registration ticket

        registrationTicket = new RegistrationTicket();
        registrationTicket.setStatus("ok");
        registrationTicket.setUserTicket(newUserTicket);

        return registrationTicket;
    }

    public TopicTicket defineClipTopic(final ClipTopic clipTopic) {
        TopicTicket topicTicket = null;

        return topicTicket;
    }
}
