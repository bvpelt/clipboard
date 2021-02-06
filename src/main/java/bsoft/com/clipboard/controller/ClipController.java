package bsoft.com.clipboard.controller;

import bsoft.com.clipboard.model.Clipboard;
import bsoft.com.clipboard.model.RegistrationTicket;
import bsoft.com.clipboard.model.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@NoArgsConstructor
public class ClipController {

    @RequestMapping(value = "/registeruser")
    public ResponseEntity<RegistrationTicket> registerUser(@RequestBody User user) {
        log.info("ClipController received request for /registeruser with user - name: {}, endpoint: {}", user.getName(), user.getEndpoint());
        ResponseEntity<RegistrationTicket> registrationTicketResponseEntity = null;
        RegistrationTicket registrationTicket = null;

        // Input validation
        boolean valid = true;

        if (user.getEmail() == null || ((user.getEmail() != null) && user.getEmail().length() ==0)) {
            log.warn("ClipController invalid input, no email for user: {}", user);
            valid = false;
        }
        if (user.getEndpoint() == null || ((user.getEndpoint() != null) && user.getEndpoint().length() ==0)) {
            log.warn("ClipController invalid input, no endpoint for user: {}", user);
            valid = false;
        }
        if (user.getName() == null || ((user.getName() != null) && user.getName().length() ==0)) {
            log.warn("ClipController invalid input, no name for user: {}", user);
            valid = false;
        }

        // if valid input register user
        if (valid) {
            Clipboard clipboard = new Clipboard();

            registrationTicket = clipboard.registerUser(user);

            registrationTicketResponseEntity = ResponseEntity.ok(registrationTicket);
            log.debug("ClipController registered user");
        } else {
            registrationTicket = new RegistrationTicket();
            registrationTicket.setErrorMessage("Input does not conform to requirements");
            registrationTicket.setStatus("Error");
            registrationTicketResponseEntity = ResponseEntity.badRequest().body(registrationTicket);
        }

        return registrationTicketResponseEntity;
    }
}
