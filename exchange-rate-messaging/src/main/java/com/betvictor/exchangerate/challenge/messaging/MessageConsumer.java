package com.betvictor.exchangerate.challenge.messaging;

import com.betvictor.exchangerate.challenge.messaging.event.ExchangeOperationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);

    private final ExchangeOperationRequestEndpoint endpoint;
    private final RabbitTemplate rabbitTemplate;

    public MessageConsumer(ExchangeOperationRequestEndpoint endpoint,
                           RabbitTemplate rabbitTemplate) {
        this.endpoint = endpoint;
        this.rabbitTemplate = rabbitTemplate;
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