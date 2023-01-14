package com.betvictor.exchangerate.challenge.messaging;

import com.betvictor.exchangerate.challenge.messaging.event.ExchangeOperationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessagePublisher {

    private static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void send(UUID contextId, OperationType operationType, Metadata metadata, String callback) {
        var request = new ExchangeOperationRequest(contextId, operationType, metadata, callback);

        rabbitTemplate.convertAndSend(this.queue.getName(), request);

        LOG.debug("Send Message: contextId={}, operationType={}, metadata={}, callbackURL={}",
                contextId, operationType, metadata, callback);
    }
}
