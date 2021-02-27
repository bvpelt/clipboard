package bsoft.com.clipboard.controller;

import bsoft.com.clipboard.config.RabbitConfiguration;
import bsoft.com.clipboard.model.ClipTopic;
import bsoft.com.clipboard.model.Clipboard;
import bsoft.com.clipboard.model.PostMessage;
import bsoft.com.clipboard.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import liquibase.pro.packaged.C;
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
public class PostMessageController {

    private Clipboard clipboard;
    private RabbitTemplate rabbitTemplate;
    private FanoutExchange fanoutExchange;
    private String exchangeName;

    @Autowired
    public PostMessageController(final Clipboard clipboard,
                                 final RabbitTemplate rabbitTemplate,
                                 final FanoutExchange fanoutExchange,
                                 final  String exchangeName) {
        this.clipboard = clipboard;
        this.rabbitTemplate = rabbitTemplate;
        this.fanoutExchange = fanoutExchange;
        this.exchangeName = exchangeName;
    }

    @Operation(summary = "Post a message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message is posted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostMessage.class))}),
            @ApiResponse(responseCode = "400", description = "Bad parameters",
                    content = @Content)})
    @RequestMapping(value = "/postmessage", method = RequestMethod.POST)
    public ResponseEntity<PostMessage> postMessage(@RequestBody PostMessage postMessage,
                                                   @RequestHeader("x-api-key") String apiKey) {
        log.info("PostMessageController received message x-api-key: {}, name: {}, message: {}", apiKey, postMessage.getClipTopicName(), postMessage.getMessage());
        ResponseEntity<PostMessage> clipTopicResponse = ResponseEntity.ok(postMessage);

        if ((apiKey == null) || (apiKey.length() == 0)){
            throw new BadParameterException("API Key required");
        }
        log.info("Valid api key: {}", apiKey);

        //
        // use api-key to get user
        //
        List<User> user = clipboard.getUserFromApiKey(apiKey);
        if ((user == null) || (user.size() !=1)) {
            throw new BadParameterException("Invalid API key");
        }
        log.info("User: {}, API-key: {}", user.get(0).getName(), apiKey);

        if ((postMessage.getMessage() == null) || (postMessage.getMessage().length() == 0)) {
            throw new BadParameterException("No message specified");
        }

        if ((postMessage.getClipTopicName() == null) || (postMessage.getClipTopicName().length() == 0)) {
            throw new BadParameterException("No topic specified");
        }

        Optional<ClipTopic> clipTopic = clipboard.getClipTopicByName(postMessage.getClipTopicName());
        if ((clipTopic == null) || !clipTopic.isPresent()) {
            throw new BadParameterException("Topic unknown");
        }

        log.info("Find subscription for user: {} with topic: {}", user.get(0).getName(), postMessage.getClipTopicName());
        boolean validSubscription = clipboard.checkSubscription(user.get(0), clipTopic.get());
        log.info("Subscription found: {}", validSubscription);

        if (!validSubscription) {
            throw new BadParameterException("User has no subscriptions to specified topic");
        }

        //
        // post message to rabbitmq with name cliptopic.name
        //
        sendMessage(postMessage);


        return clipTopicResponse;
    }

    private void sendMessage(final PostMessage postMessage) {
        try {
            log.info("Before send");
            rabbitTemplate.convertAndSend(exchangeName, "", postMessage);
            log.info("After send");
        } catch (Exception e) {
            log.error("Prolem sending message - {}", e);
        }
    }

    private Queue getQueue(final String name) {
        boolean durable = true;
        return new Queue(name, durable); // use durable queues
    }

    private Binding getBinding(final Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}
