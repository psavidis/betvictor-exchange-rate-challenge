package com.betvictor.exchangerate.challenge.messaging;

import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeOperationRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType.CONVERSION;
import static com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType.EXCHANGE_RATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessagePublisherTest {

    @Mock
    private RabbitTemplate template;

    @Mock
    private Queue queue;

    @Captor
    private ArgumentCaptor<ExchangeOperationRequest> captor;

    private MessagePublisher publisher;

    @BeforeEach
    void init() {
        publisher = new MessagePublisher(template, queue);
    }

    @Test
    void conversion_happyPath() {
        var request = new Object();
        var contextId = UUID.randomUUID();
        var metadata = new Metadata(null, request);
        var url = "url";

        publisher.send(contextId, CONVERSION, metadata, url);

        verify(template).convertAndSend(eq(queue.getName()), captor.capture());

        var result = captor.getValue();

        assertEquals("url", result.callBackURL());
        assertEquals(contextId, result.contextId());
        assertEquals(metadata, result.metadata());
        assertEquals(CONVERSION, result.operationType());
        assertNotNull(result.timestamp());
    }

    @Test
    void exchangeRate_happyPath() {
        var request = new Object();
        var contextId = UUID.randomUUID();
        var metadata = new Metadata(null, request);
        var url = "url";

        publisher.send(contextId, EXCHANGE_RATE, metadata, url);

        verify(template).convertAndSend(eq(queue.getName()), captor.capture());

        var result = captor.getValue();

        assertEquals("url", result.callBackURL());
        assertEquals(contextId, result.contextId());
        assertEquals(metadata, result.metadata());
        assertEquals(EXCHANGE_RATE, result.operationType());
        assertNotNull(result.timestamp());
    }

}