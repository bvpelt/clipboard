package bsoft.com.clipboard.controller;

import bsoft.com.clipboard.model.RegistrationTicket;
import bsoft.com.clipboard.storage.Reader;
import bsoft.com.clipboard.storage.StorageReaderTask;
import bsoft.com.clipboard.storage.StorageReaderTaskList;
import bsoft.com.clipboard.storage.TaskInfo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import liquibase.pro.packaged.T;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

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
public class ReaderController {

    private Reader reader;


    @Autowired
    public ReaderController(final Reader reader) {
        this.reader = reader;
    }

    @Operation(summary = "All readers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All readers",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StorageReaderTaskList.class))}),
            @ApiResponse(responseCode = "400", description = "Bad parameters",
                    content = @Content)})
    @RequestMapping(value = "/readers", method = RequestMethod.GET)
    public ResponseEntity<StorageReaderTaskList> allReaders() {
        ResponseEntity<StorageReaderTaskList> StorageReaderTaskResponse = null;

        StorageReaderTask[] tasklist = reader.getStorageReaderTasks();
        StorageReaderTaskList result = new StorageReaderTaskList();
        TaskInfo[] taskInfos = new TaskInfo[tasklist.length];
        for (int i = 0; i < tasklist.length; i++) {
            if (tasklist[i] != null) {
                taskInfos[i] = new TaskInfo();
                taskInfos[i].setInterval(tasklist[i].getInterval());
                taskInfos[i].setName(tasklist[i].getName());
                taskInfos[i].setGoOn(tasklist[i].isGoOn());
            }
        }
        result.setTaskInfos(taskInfos);

        StorageReaderTaskResponse = ResponseEntity.ok(result);

        return StorageReaderTaskResponse;
    }

    @Operation(summary = "Start readers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Readers started",
                    content = {@Content(mediaType = "application/text")}),
            @ApiResponse(responseCode = "404", description = "Readers not found",
                    content = @Content)})
    @RequestMapping(value = "/readers/start", method = RequestMethod.GET)
    public ResponseEntity<String> startReaders() {
        ResponseEntity<String> publisherResponse = null;

        int numberStarted = reader.startReaders();

        String result = "Number threads started: " + numberStarted;

        publisherResponse = ResponseEntity.ok(result);

        return publisherResponse;
    }

    @Operation(summary = "Stop readers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Readers stopped",
                    content = {@Content(mediaType = "application/text")}),
            @ApiResponse(responseCode = "404", description = "Readers not found",
                    content = @Content)})
    @RequestMapping(value = "/readers/stop", method = RequestMethod.GET)
    public ResponseEntity<String> stopReaders() {
        ResponseEntity<String> publisherResponse = null;

        int numberStarted = reader.stopReaders();

        String result = "Number threads stopped: " + numberStarted;

        publisherResponse = ResponseEntity.ok(result);

        return publisherResponse;
    }


}

