package com.betvictor.exchangerate.challenge.app.messaging.endpoint;

import com.betvictor.exchangerate.challenge.app.messaging.exception.InvalidEventException;
import com.betvictor.exchangerate.challenge.messaging.ExchangeOperationRequestEndpoint;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeOperationRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ExchangeOperationRequestEndpointImpl implements ExchangeOperationRequestEndpoint {

    private static final long EVENT_TIMEOUT_MILLIS = 60_000L;

    private final ExchangeOperationRequestHandler handler;

    public ExchangeOperationRequestEndpointImpl(ExchangeOperationRequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onEvent(ExchangeOperationRequest event) {
        validate(event);

        if (event.isExchange()) {
            handler.handleExchange(event);
            return;
        }

        if (event.isConversion()) {
            handler.handleConversion(event);
            return;
        }

        throw new IllegalArgumentException("Event is neither conversion nor exchange");
    }

    private void validate(ExchangeOperationRequest event) {
        var timestamp = event.timestamp();
        var now = Instant.now();

        if (now.isBefore(timestamp)) {
            throw new InvalidEventException(event, "Cannot process event with future timestamp!!!");
        }

        var millisPassed = getMillisPassedFromEvent(event);

        if (millisPassed > EVENT_TIMEOUT_MILLIS) {
            throw new InvalidEventException(event, "Cannot process message due to timeout");
        }
    }

    private long getMillisPassedFromEvent(ExchangeOperationRequest event) {
        var timestamp = event.timestamp();
        var now = Instant.now();

        return now.toEpochMilli() - timestamp.toEpochMilli();
    }

}