package com.betvictor.exchangerate.challenge.messaging;

import com.betvictor.exchangerate.challenge.messaging.event.ExchangeOperationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Component for the actual consumption of messages from RabbitMQ from respective queue.
 * <p>
 * Messages that Fail will be ignored for now. In real life, they could be re-routed
 * to a dead-letter queue.
 */
@Component
public class MessageConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);

    private final ExchangeOperationRequestEndpoint endpoint;

    public MessageConsumer(ExchangeOperationRequestEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload ExchangeOperationRequest event) {
        LOG.info("Event Received : {}", event);
        try {
            endpoint.onEvent(event);
        } catch (Exception e) {
            LOG.error("Failed to process event: {} due to : {}. Ignoring event...", event, e);
        }
    }
}