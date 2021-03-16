package bsoft.com.clipboard.application.controller;


import bsoft.com.clipboard.application.model.Clipboard;
import bsoft.com.clipboard.storage.model.Publisher;
import bsoft.com.clipboard.storage.model.RegistrationTicket;
import bsoft.com.clipboard.storage.model.PublisherList;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                title = "Clipboard API",
                version = "0.1",
                description = "The clipboard API provides an API to let clients send messages"
                        + " It is designed to support both administration of publishers/topics and subscriptions",
                contact = @Contact(
                        name = "Bart van Pelt",
                        email = "brtvnplt@gmail.com"
                ),
                license = @License(
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html",
                        name = "Apache 2.0"
                )
        )
)

@RestController
@Slf4j
@NoArgsConstructor
public class PublisherController {

    private Clipboard clipboard;

    @Autowired
    public PublisherController(final Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    @Operation(summary = "Register new publisher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New publisher is registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationTicket.class))}),
            @ApiResponse(responseCode = "400", description = "Bad parameters",
                    content = @Content)})
    @RequestMapping(value = "/publishers", method = RequestMethod.POST)
    public ResponseEntity<Publisher> registerPublisher(@RequestBody Publisher publisher) {
        log.info("PublisherController received request for /registerPublisher with Publisher - name: {}, endpoint: {}", publisher.getName(), publisher.getEndpoint());
        ResponseEntity<Publisher> registrationTicketResponseEntity = null;
        Publisher registrationTicket = null;

        // validate fields
        // if valid
        //   store new entry
        // else
        //   bad parameters

        boolean valid = true;

        if (publisher.getEmail() == null || ((publisher.getEmail() != null) && publisher.getEmail().length() == 0)) {
            log.warn("PublisherController invalid input, no email for Publisher: {}", publisher);
            valid = false;
        } else if (publisher.getEmail().length() > 127) {
            log.warn("PublisherController invalid input, email to long Publisher: {}", publisher);
            valid = false;
        }

        if (publisher.getEndpoint() == null || ((publisher.getEndpoint() != null) && publisher.getEndpoint().length() == 0)) {
            log.warn("PublisherController invalid input, no endpoint for Publisher: {}", publisher);
            valid = false;
        } else if (publisher.getEndpoint().length() > 127) {
            log.warn("PublisherController invalid input, endpoint to long for Publisher: {}", publisher);
            valid = false;
        }

        if (publisher.getName() == null || ((publisher.getName() != null) && publisher.getName().length() == 0)) {
            log.warn("PublisherController invalid input, no name for Publisher: {}", publisher);
            valid = false;
        } else if (publisher.getName().length() > 23) {
            log.warn("PublisherController invalid input, name to long for Publisher: {}", publisher);
            valid = false;

        }

        // if valid input register publisher
        if (valid) {
            if (publisher.getId() != null) {
                throw new BadParameterException("Publisher id invalid");
            } else {
                registrationTicket = clipboard.registerPublisher(publisher);

                registrationTicketResponseEntity = ResponseEntity.ok(registrationTicket);
                log.debug("PublisherController registered publisher");
            }

        } else {
            throw new BadParameterException("Invalid parameters");
        }

        return registrationTicketResponseEntity;
    }

    @Operation(summary = "Get all known publishers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found publishers",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PublisherList.class))}),
            @ApiResponse(responseCode = "404", description = "No publishers found",
                    content = @Content)})
    @RequestMapping(value = "/publishers", method = RequestMethod.GET)
    public ResponseEntity<PublisherList> getPublishers() {
        ResponseEntity<PublisherList> publisherResponse = null;
        List<Publisher> publishers = null;

        publishers = clipboard.getPublishers();

        PublisherList publisherList = new PublisherList();
        publisherList.setPublishers(publishers.toArray(new Publisher[publishers.size()]));

        publisherResponse = ResponseEntity.ok(publisherList);

        return publisherResponse;
    }

    @Operation(summary = "Get a publisher by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the publisher",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publisher.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Publisher not found",
                    content = @Content)})
    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        ResponseEntity<Publisher> publisherResponse = null;
        Optional<Publisher> publisher;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        publisher = clipboard.getPublisherById(id);

        if (publisher.isPresent()) {
            publisherResponse = ResponseEntity.ok(publisher.get());
        } else {
            throw new PublisherNotFoundException("Publisher not found");
        }
        return publisherResponse;
    }

    @Operation(summary = "Delete a publisher by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the publisher",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publisher.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)
    })
    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Publisher> deletePublisherById(@PathVariable Long id) {
        ResponseEntity<Publisher> publisherResponse = null;
        Optional<Publisher> publisher;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        publisher = clipboard.getPublisherById(id);

        if ((publisher != null) && publisher.isPresent()) {

            clipboard.deletePublisherById(id);

            publisherResponse = ResponseEntity.ok(publisher.get());
        } else {
            throw new BadParameterException("Invalid id specified");
        }

        return publisherResponse;
    }

    @Operation(summary = "Change status of publisher to confirmed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status to confirmed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publisher.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Publisher not found",
                    content = @Content)})
    @RequestMapping(value = "/publishers/{id}/confirm", method = RequestMethod.PUT)
    public ResponseEntity<Publisher> updatePublisherStatusById(@PathVariable Long id) {
        ResponseEntity<Publisher> publisherResponse = null;
        Optional<Publisher> publisher;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        publisher = clipboard.confirmPublisher(id);

        if (publisher.isPresent()) {
            publisherResponse = ResponseEntity.ok(publisher.get());
        } else {
            throw new PublisherNotFoundException("Publisher not found");
        }
        return publisherResponse;
    }

    @Operation(summary = "Change status of publisher to disabled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status to disabled",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publisher.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Publisher not found",
                    content = @Content)})
    @RequestMapping(value = "/publishers/{id}/disabled", method = RequestMethod.PUT)
    public ResponseEntity<Publisher> disablePublisherStatusById(@PathVariable Long id) {
        ResponseEntity<Publisher> publisherResponse = null;
        Optional<Publisher> publisher;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        publisher = clipboard.disablePublisher(id);

        if (publisher.isPresent()) {
            publisherResponse = ResponseEntity.ok(publisher.get());
        } else {
            throw new PublisherNotFoundException("Publisher not found");
        }
        return publisherResponse;
    }

    @Operation(summary = "Change status of publisher to removed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status to removed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publisher.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Publisher not found",
                    content = @Content)})
    @RequestMapping(value = "/publishers/{id}/removed", method = RequestMethod.PUT)
    public ResponseEntity<Publisher> removePublisherStatusById(@PathVariable Long id) {
        ResponseEntity<Publisher> publisherResponse = null;
        Optional<Publisher> publisher;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        publisher = clipboard.removePublisher(id);

        if (publisher.isPresent()) {
            publisherResponse = ResponseEntity.ok(publisher.get());
        } else {
            throw new PublisherNotFoundException("Publisher not found");
        }
        return publisherResponse;
    }

    @Operation(summary = "Add subscriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add subscriptions",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publisher.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Publisher not found",
                    content = @Content)})
    @RequestMapping(value = "/publishers/{id}/subscriptions", method = RequestMethod.PUT)
    public ResponseEntity<Publisher> addSubscriptions(@PathVariable Long id, @Parameter String[] names) {
        ResponseEntity<Publisher> publisherResponse = null;
        Optional<Publisher> publisher;

        if (id <= 0L) {
            throw new BadParameterException("Invalid id specified");
        }

        publisher = clipboard.addPublisherSubscriptions(id, names);

        if (publisher.isPresent()) {
            publisherResponse = ResponseEntity.ok(publisher.get());
        } else {
            throw new PublisherNotFoundException("Publisher not found");
        }
        return publisherResponse;
    }


}

