package com.bsoft.clipboard.webservice.controller;


import com.bsoft.clipboard.persist.model.RegistrationTicket;
import com.bsoft.clipboard.persist.model.User;
import com.bsoft.clipboard.persist.model.UserList;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@NoArgsConstructor
public class ClipController {

    private Clipboard clipboard;

    @Autowired
    public ClipController(final Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<UserList> registeredUsers() {
        ResponseEntity<UserList> userResponse = null;
        List<User> users = null;

        users = clipboard.getUsers();

        UserList userList = new UserList();
        userList.setUsers(users.toArray(new User[users.size()]));

        userResponse = ResponseEntity.ok(userList);

        return userResponse;
    }


    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public ResponseEntity<RegistrationTicket> registerUser(@RequestBody User user) {
        log.info("ClipController received request for /registeruser with user - name: {}, endpoint: {}", user.getName(), user.getEndpoint());
        ResponseEntity<RegistrationTicket> registrationTicketResponseEntity = null;
        RegistrationTicket registrationTicket = null;

        // Input validation
        boolean valid = true;

        if (user.getEmail() == null || ((user.getEmail() != null) && user.getEmail().length() == 0)) {
            log.warn("ClipController invalid input, no email for user: {}", user);
            valid = false;
        }
        if (user.getEndpoint() == null || ((user.getEndpoint() != null) && user.getEndpoint().length() == 0)) {
            log.warn("ClipController invalid input, no endpoint for user: {}", user);
            valid = false;
        }
        if (user.getName() == null || ((user.getName() != null) && user.getName().length() == 0)) {
            log.warn("ClipController invalid input, no name for user: {}", user);
            valid = false;
        }

        // if valid input register user
        if (valid) {

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
