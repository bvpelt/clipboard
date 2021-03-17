package bsoft.com.clipboard.application.controller;

import bsoft.com.clipboard.storage.service.BadParameterException;
import bsoft.com.clipboard.storage.service.Clipboard;
import bsoft.com.clipboard.storage.model.ClipTopic;
import bsoft.com.clipboard.storage.model.ClipTopicList;

import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@Slf4j
@NoArgsConstructor
public class ClipTopicController {

    private Clipboard clipboard;

    @Autowired
    public ClipTopicController(final Clipboard clipboard) {
        this.clipboard = clipboard;
    }


    @Operation(summary = "Register new cliptopic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New cliptopic is registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClipTopic.class))}),
            @ApiResponse(responseCode = "400", description = "Bad parameters",
                    content = @Content)})
    @RequestMapping(value = "/cliptopics", method = RequestMethod.POST)
    public ResponseEntity<ClipTopic> registerClipTopic(@RequestBody ClipTopic clipTopic) {
        log.info("ClipTopicController received request for /cliptopics with clipTopic - name: {}, description: {}", clipTopic.getName(), clipTopic.getDescription());
        ResponseEntity<ClipTopic> clipTopicResponse = null;
        ClipTopic registeredClipTopic = null;

        // validate fields
        // if valid
        //   store new entry
        // else
        //   bad parameters
        boolean valid = true;

        if (clipTopic.getName() == null || ((clipTopic.getName() != null) && clipTopic.getName().length() == 0)) {
            log.warn("ClipTopicController invalid input, no name for clipTopic: {}", clipTopic);
            valid = false;
        } else if (clipTopic.getName().length() > 23) {
            log.warn("ClipTopicController invalid input, name to long clipTopic: {}", clipTopic);
            valid = false;
        }

        if (clipTopic.getDescription() != null) {
            if (clipTopic.getDescription().length() > 127) {
                log.warn("ClipTopicController invalid input, description to long clipTopic: {}", clipTopic);
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

