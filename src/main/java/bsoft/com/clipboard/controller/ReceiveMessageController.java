package bsoft.com.clipboard.controller;

import bsoft.com.clipboard.config.ConfigElements;
import bsoft.com.clipboard.model.ClipTopic;
import bsoft.com.clipboard.model.Clipboard;
import bsoft.com.clipboard.model.PostMessage;
import bsoft.com.clipboard.model.Publisher;
import com.rabbitmq.client.Channel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@NoArgsConstructor
public class ReceiveMessageController {

    private Clipboard clipboard;
    private RabbitTemplate rabbitTemplate;
    private FanoutExchange fanoutExchange;
    private String exchangeName;
    private Channel channel;
    private ConfigElements configElements;

    @Autowired
    public ReceiveMessageController(final Clipboard clipboard,
                                    final RabbitTemplate rabbitTemplate,
                                    final FanoutExchange fanoutExchange,
                                    final String exchangeName,
                                    final Channel channel,
                                    final ConfigElements configElements) {
        this.clipboard = clipboard;
        this.rabbitTemplate = rabbitTemplate;
        this.fanoutExchange = fanoutExchange;
        this.exchangeName = exchangeName;
        this.channel = channel;
        this.configElements = configElements;
    }

    @Operation(summary = "Receive a message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message is posted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostMessage.class))}),
            @ApiResponse(responseCode = "400", description = "Bad parameters",
                    content = @Content)})
    @RequestMapping(value = "/receivemessage", method = RequestMethod.POST)
    public ResponseEntity<PostMessage> receiveMessage(@RequestBody PostMessage postMessage,
                                                   @RequestHeader("x-api-key") String apiKey) {
        log.info("ReceiveMessageController received message x-api-key: {}, name: {}, message: {}", apiKey, postMessage.getClipTopicName(), postMessage.getMessage());
        ResponseEntity<PostMessage> clipTopicResponse = ResponseEntity.ok(postMessage);

        /*
        if ((apiKey == null) || (apiKey.length() == 0)) {
            throw new BadParameterException("API Key required");
        }
        log.info("Valid api key: {}", apiKey);
*/
        log.info("Received message: {}", postMessage.toString());

        return clipTopicResponse;
    }


}

