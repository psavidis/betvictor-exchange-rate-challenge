package com.betvictor.exchangerate.challenge.messaging;

import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeOperationRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.Metadata;
import com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

/**
 * Component actually responsible for publishing events to rabbitMQ operation queue.
 */
@Component
public class MessagePublisher {

    private static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);

    private final RabbitTemplate template;
    private final Queue queue;

    public MessagePublisher(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void send(UUID contextId, OperationType operationType, Metadata metadata, String callback) {
        var request = new ExchangeOperationRequest(contextId, operationType, metadata, callback, Instant.now());

        template.convertAndSend(this.queue.getName(), request);

        LOG.debug("Send Message: contextId={}, operationType={}, metadata={}, callbackURL={}",
                contextId, operationType, metadata, callback);
    }
}
