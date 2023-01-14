package com.betvictor.exchangerate.challenge.client.app.example.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller simulating the client's callback
 */
@RestController
@RequestMapping("/client")
public class CallbackController {

    private static final Logger LOG = LoggerFactory.getLogger(CallbackController.class);

    @PostMapping("/callback")
    void receiveCallback(@RequestParam UUID contextId, @RequestBody String json) {
        LOG.info("Received Callback for ContextId: {} with Data: {}", contextId, json);
    }


}