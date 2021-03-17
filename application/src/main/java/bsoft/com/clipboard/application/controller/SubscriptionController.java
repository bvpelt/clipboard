package bsoft.com.clipboard.application.controller;


import bsoft.com.clipboard.storage.service.Clipboard;
import bsoft.com.clipboard.storage.model.Subscription;
import bsoft.com.clipboard.storage.model.SubscriptionList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@NoArgsConstructor
public class SubscriptionController {

    private Clipboard clipboard;

    @Autowired
    public SubscriptionController(final Clipboard clipboard) {
        this.clipboard = clipboard;
    }


    @Operation(summary = "Get all known subscriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found subscriptions",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subscription.class))}),
            @ApiResponse(responseCode = "404", description = "No subscriptions found",
                    content = @Content)})
    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
    public ResponseEntity<SubscriptionList> getSubscriptions() {
        ResponseEntity<SubscriptionList> subscriptionResponse = null;
        List<Subscription> subscriptions = null;

        subscriptions = clipboard.getSubscriptions();

        SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.setSubscriptions(subscriptions.toArray(new Subscription[subscriptions.size()]));

        subscriptionResponse = ResponseEntity.ok(subscriptionList);

        return subscriptionResponse;
    }

}

