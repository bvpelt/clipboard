package bsoft.com.clipboard.controller;

import bsoft.com.clipboard.model.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                title = "Clipboard API",
                version = "r0.1",
                description = "The clipboard API provides an API to let clients send messages"
                        + " It is designed to support both administration of users/topics and subscriptions",
                contact = @Contact(
                        name = "Bart van Pelt",
                        email = "brtvnplt@gmail.com"
                ),
                license = @License(
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html",
                        name = "Apache 2.0"
                )
        ),
        security = {
                @SecurityRequirement(
                        name = "accessToken"
                )
        }
)

@RestController
@Slf4j
@NoArgsConstructor
public class ClipController {

    private Clipboard clipboard;

    @Autowired
    public ClipController(final Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New user is registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationTicket.class))}),
            @ApiResponse(responseCode = "400", description = "Bad parameters",
                    content = @Content)})
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        log.info("ClipController received request for /registeruser with user - name: {}, endpoint: {}", user.getName(), user.getEndpoint());
        ResponseEntity<User> registrationTicketResponseEntity = null;
        User registrationTicket = null;

        // validate fields
        // if valid
        //   store new entry
        // else
        //   bad parameters

        boolean valid = true;

        if (user.getEmail() == null || ((user.getEmail() != null) && user.getEmail().length() == 0)) {
            log.warn("ClipController invalid input, no email for user: {}", user);
            valid = false;
        } else if (user.getEmail().length() > 127) {
            log.warn("ClipController invalid input, email to long user: {}", user);
            valid = false;
        }

        if (user.getEndpoint() == null || ((user.getEndpoint() != null) && user.getEndpoint().length() == 0)) {
            log.warn("ClipController invalid input, no endpoint for user: {}", user);
            valid = false;
        } else if (user.getEndpoint().length() > 127) {
            log.warn("ClipController invalid input, endpoint to long for user: {}", user);
            valid = false;
        }

        if (user.getName() == null || ((user.getName() != null) && user.getName().length() == 0)) {
            log.warn("ClipController invalid input, no name for user: {}", user);
            valid = false;
        } else if (user.getName().length() > 23) {
            log.warn("ClipController invalid input, name to long for user: {}", user);
            valid = false;

        }

        // if valid input register user
        if (valid) {
            if (user.getId() != null) {
                throw new BadParameterException("User id invalid");
            } else {
                registrationTicket = clipboard.registerUser(user);

                registrationTicketResponseEntity = ResponseEntity.ok(registrationTicket);
                log.debug("ClipController registered user");
            }

        } else {
            throw new BadParameterException("Invalid parameters");
        }

        return registrationTicketResponseEntity;
    }

    @Operation(summary = "Get all known users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserList.class))}),
            @ApiResponse(responseCode = "404", description = "No users found",
                    content = @Content)})
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<UserList> getUsers() {
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
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        ResponseEntity<User> userResponse = null;
        Optional<User> user;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        user = clipboard.getUserById(id);

        if (user.isPresent()) {
            userResponse = ResponseEntity.ok(user.get());
        } else {
            throw new UserNotFoundException("User not found");
        }
        return userResponse;
    }

    @Operation(summary = "Delete a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)
    })
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        ResponseEntity<User> userResponse = null;
        Optional<User> user;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        user = clipboard.getUserById(id);

        if ((user != null) && user.isPresent()) {

            clipboard.deleteUserById(id);

            userResponse = ResponseEntity.ok(user.get());
        } else {
            throw new BadParameterException("Invalid id specified");
        }

        return userResponse;
    }

    @Operation(summary = "Change status of user to confirmed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status to confirmed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @RequestMapping(value = "/users/{id}/confirm", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUserStatusById(@PathVariable Long id) {
        ResponseEntity<User> userResponse = null;
        Optional<User> user;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        user = clipboard.confirmUser(id);

        if (user.isPresent()) {
            userResponse = ResponseEntity.ok(user.get());
        } else {
            throw new UserNotFoundException("User not found");
        }
        return userResponse;
    }

    @Operation(summary = "Change status of user to disabled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status to disabled",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @RequestMapping(value = "/users/{id}/disabled", method = RequestMethod.PUT)
    public ResponseEntity<User> disableUserStatusById(@PathVariable Long id) {
        ResponseEntity<User> userResponse = null;
        Optional<User> user;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        user = clipboard.disableUser(id);

        if (user.isPresent()) {
            userResponse = ResponseEntity.ok(user.get());
        } else {
            throw new UserNotFoundException("User not found");
        }
        return userResponse;
    }

    @Operation(summary = "Change status of user to removed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status to removed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @RequestMapping(value = "/users/{id}/removed", method = RequestMethod.PUT)
    public ResponseEntity<User> removeUserStatusById(@PathVariable Long id) {
        ResponseEntity<User> userResponse = null;
        Optional<User> user;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        user = clipboard.removeUser(id);

        if (user.isPresent()) {
            userResponse = ResponseEntity.ok(user.get());
        } else {
            throw new UserNotFoundException("User not found");
        }
        return userResponse;
    }

    @Operation(summary = "Register new cliptopic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New user is registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClipTopic.class))}),
            @ApiResponse(responseCode = "400", description = "Bad parameters",
                    content = @Content)})
    @RequestMapping(value = "/cliptopics", method = RequestMethod.POST)
    public ResponseEntity<ClipTopic> registerClipTopic(@RequestBody ClipTopic clipTopic) {
        log.info("ClipController received request for /registerClipTopic with clipTopic - name: {}, description: {}", clipTopic.getName(), clipTopic.getDescription());
        ResponseEntity<ClipTopic> clipTopicResponse = null;
        ClipTopic registeredClipTopic = null;

        // validate fields
        // if valid
        //   store new entry
        // else
        //   bad parameters
        boolean valid = true;

        if (clipTopic.getName() == null || ((clipTopic.getName() != null) && clipTopic.getName().length() == 0)) {
            log.warn("ClipController invalid input, no name for clipTopic: {}", clipTopic);
            valid = false;
        } else if (clipTopic.getName().length() > 23) {
            log.warn("ClipController invalid input, name to long clipTopic: {}", clipTopic);
            valid = false;
        }

        if (clipTopic.getDescription() != null) {
            if (clipTopic.getDescription().length() > 127) {
                log.warn("ClipController invalid input, description to long clipTopic: {}", clipTopic);
                valid = false;
            }
        }

        if (valid) {
            registeredClipTopic = clipboard.registerClipTopic(clipTopic);
            clipTopicResponse = ResponseEntity.ok(registeredClipTopic);
        } else {
            throw new BadParameterException("Invalid parameters");
        }

        return clipTopicResponse;
    }

    @Operation(summary = "Get all known cliptopics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found cliptopics",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClipTopicList.class))}),
            @ApiResponse(responseCode = "404", description = "No cliptopics found",
                    content = @Content)})
    @RequestMapping(value = "/cliptopics", method = RequestMethod.GET)
    public ResponseEntity<ClipTopicList> getClipTopics() {
        ResponseEntity<ClipTopicList> clipTopicResponse = null;
        List<ClipTopic> clipTopics = null;

        clipTopics = clipboard.getClipTopics();

        ClipTopicList clipTopicList = new ClipTopicList();
        clipTopicList.setClipTopics(clipTopics.toArray(new ClipTopic[clipTopics.size()]));

        clipTopicResponse = ResponseEntity.ok(clipTopicList);

        return clipTopicResponse;
    }

    @Operation(summary = "Get a cliptopic by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the cliptopic",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClipTopic.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "ClipTopic not found",
                    content = @Content)})
    @RequestMapping(value = "/cliptopics/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClipTopic> getClipTopicById(@PathVariable Long id) {
        ResponseEntity<ClipTopic> clipTopicResponse = null;
        Optional<ClipTopic> clipTopic;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        clipTopic = clipboard.getClipTopicById(id);

        if (clipTopic.isPresent()) {
            clipTopicResponse = ResponseEntity.ok(clipTopic.get());
        } else {
            throw new ClipTopicNotFoundException("ClipTopic not found");
        }
        return clipTopicResponse;
    }
}

