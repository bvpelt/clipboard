package bsoft.com.clipboard.controller;

import bsoft.com.clipboard.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import liquibase.pro.packaged.U;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@NoArgsConstructor
public class ClipController {

    private Clipboard clipboard;

    @Autowired
    public ClipController(final Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    @Operation(summary = "Get all known users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserList.class)) }),
            @ApiResponse(responseCode = "404", description = "No users found",
                    content = @Content) })
    @RequestMapping(value = "/users", method=RequestMethod.GET)
    public ResponseEntity<UserList> users() {
        ResponseEntity<UserList> userResponse = null;
        List<User> users = null;

        users = clipboard.getUsers();

        UserList userList = new UserList();
        userList.setUsers(users.toArray(new User[users.size()]));

        userResponse = ResponseEntity.ok(userList);

        return userResponse;
    }

    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @RequestMapping(value = "/users/{id}", method=RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        ResponseEntity<User> userResponse = null;
        Optional<User> user;

        user = clipboard.getUserById(id);

        if (user.isPresent()) {
            userResponse = ResponseEntity.ok(user.get());
        } else {
            //userResponse = ResponseEntity.notFound().build();
            throw new UserNotFoundException("User not found");
        }
        return userResponse;
    }

    @Operation(summary = "Change status of user to confirmed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status to confirmed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @RequestMapping(value = "/users/{id}/confirm", method=RequestMethod.PUT)
    public ResponseEntity<User> updateUserStatusById(@PathVariable Long id) {
        ResponseEntity<User> userResponse = null;
        Optional<User> user;

        user = clipboard.confirmUser(id);

        if (user.isPresent()) {
            userResponse = ResponseEntity.ok(user.get());
        } else {
            //userResponse = ResponseEntity.notFound().build();
            throw new UserNotFoundException("User not found");
        }
        return userResponse;
    }

    @Operation(summary = "Change status of user to disabled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status to disabled",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @RequestMapping(value = "/users/{id}/disabled", method=RequestMethod.PUT)
    public ResponseEntity<User> disableUserStatusById(@PathVariable Long id) {
        ResponseEntity<User> userResponse = null;
        Optional<User> user;

        user = clipboard.disableUser(id);

        if (user.isPresent()) {
            userResponse = ResponseEntity.ok(user.get());
        } else {
            //userResponse = ResponseEntity.notFound().build();
            throw new UserNotFoundException("User not found");
        }
        return userResponse;
    }

    @Operation(summary = "Change status of user to removed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status to removed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @RequestMapping(value = "/users/{id}/removed", method=RequestMethod.PUT)
    public ResponseEntity<User> removeUserStatusById(@PathVariable Long id) {
        ResponseEntity<User> userResponse = null;
        Optional<User> user;

        user = clipboard.removeUser(id);

        if (user.isPresent()) {
            userResponse = ResponseEntity.ok(user.get());
        } else {
            //userResponse = ResponseEntity.notFound().build();
            throw new UserNotFoundException("User not found");
        }
        return userResponse;
    }

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New user is registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationTicket.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad parameters",
                    content = @Content) })
    @RequestMapping(value = "/registeruser", method=RequestMethod.POST)
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
            /*
            registrationTicket = new RegistrationTicket();
            registrationTicket.setErrorMessage("Input does not conform to requirements");
            registrationTicket.setStatus("Error");
            registrationTicketResponseEntity = ResponseEntity.badRequest().body(registrationTicket);
             */
            throw new BadParameterException("Invalid parameters");
        }

        return registrationTicketResponseEntity;
    }


    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New user is registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationTicket.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad parameters",
                    content = @Content) })
    @RequestMapping(value = "/registercliptopic", method=RequestMethod.POST)
    public ResponseEntity<ClipTopic> registerClipTopic(@RequestBody ClipTopic clipTopic) {
        log.info("ClipController received request for /registerClipTopic with clipTopic - name: {}, description: {}", clipTopic.getName(), clipTopic.getDescription());
        ResponseEntity<ClipTopic> registrationTicketResponseEntity = null;
        ClipTopic registeredClipTopic = null;

        registeredClipTopic = clipboard.registerClipTopic(clipTopic);
        registrationTicketResponseEntity = ResponseEntity.ok(registeredClipTopic);

        return registrationTicketResponseEntity;
    }
}

