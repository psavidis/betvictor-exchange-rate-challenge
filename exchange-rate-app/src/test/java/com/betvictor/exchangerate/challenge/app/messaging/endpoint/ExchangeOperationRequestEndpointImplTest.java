package com.betvictor.exchangerate.challenge.app.messaging.endpoint;

import com.betvictor.exchangerate.challenge.app.messaging.exception.InvalidEventException;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeOperationRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static com.betvictor.exchangerate.challenge.client.DataSourceClientType.EXCHANGE_RATE_HOST;
import static com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType.CONVERSION;
import static com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType.EXCHANGE_RATE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ExchangeOperationRequestEndpointImplTest {

    @Mock
    private ExchangeOperationRequestHandler handler;

    private ExchangeOperationRequestEndpointImpl endpoint;

    @BeforeEach
    void init() {
        this.endpoint = new ExchangeOperationRequestEndpointImpl(handler);
    }

    @Test
    void event_withTimestamp_Exceeding_oneMinute_shouldThrow_InvalidEventException() {
        var request = new ExchangeOperationRequest(
                UUID.randomUUID(),
                EXCHANGE_RATE,
                new Metadata(EXCHANGE_RATE_HOST, new Object()),
                "url",
                Instant.now().minusMillis(60_000L)
        );

        assertThrows(InvalidEventException.class, () -> endpoint.onEvent(request));
    }

    @Test
    void event_withTimestamp_inTheFuture_shouldThrow_InvalidEventException() {
        var request = new ExchangeOperationRequest(
                UUID.randomUUID(),
                EXCHANGE_RATE,
                new Metadata(EXCHANGE_RATE_HOST, new Object()),
                "url",
                Instant.now().plusMillis(10_000L)
        );

        assertThrows(InvalidEventException.class, () -> endpoint.onEvent(request));
    }

    @Test
    void isExchange_event_shouldInvoke_handleExchange() {
        var request = new ExchangeOperationRequest(
                UUID.randomUUID(),
                EXCHANGE_RATE,
                new Metadata(EXCHANGE_RATE_HOST, new Object()),
                "url",
                Instant.now()
        );

        endpoint.onEvent(request);

        verify(handler).handleExchange(any(ExchangeOperationRequest.class));
    }

    @Test
    void isConversion_event_shouldInvoke_handleConversion() {
        var request = new ExchangeOperationRequest(
                UUID.randomUUID(),
                CONVERSION,
                new Metadata(EXCHANGE_RATE_HOST, new Object()),
                "url",
                Instant.now()
        );

        endpoint.onEvent(request);

        verify(handler).handleConversion(any(ExchangeOperationRequest.class));
    }
}
