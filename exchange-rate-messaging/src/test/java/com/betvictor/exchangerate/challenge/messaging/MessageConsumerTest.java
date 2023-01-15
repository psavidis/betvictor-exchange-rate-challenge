package com.betvictor.exchangerate.challenge.messaging;


import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeOperationRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.Metadata;
import com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessageConsumerTest {

    @Mock
    private ExchangeOperationRequestEndpoint endpoint;

    private MessageConsumer consumer;

    private ExchangeOperationRequest event = new ExchangeOperationRequest(
            UUID.randomUUID(),
            OperationType.CONVERSION,
            new Metadata(null, null),
            "url",
            Instant.now()
    );

    @BeforeEach
    void init() {
        consumer = new MessageConsumer(endpoint);
    }

    @Test
    void happyPath() {
        consumer.receive(event);

        verify(endpoint).onEvent(event);
    }

    @Test
    void endpointThrowsException_shouldNot_PropagateException() {
        doThrow(RuntimeException.class).when(endpoint).onEvent(any());

        assertDoesNotThrow(() -> consumer.receive(event));
    }
}
