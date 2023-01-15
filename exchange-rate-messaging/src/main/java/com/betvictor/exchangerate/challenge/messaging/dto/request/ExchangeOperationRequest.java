package com.betvictor.exchangerate.challenge.messaging.dto.request;

import com.betvictor.exchangerate.challenge.messaging.event.Event;

import java.time.Instant;
import java.util.UUID;

import static com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType.CONVERSION;
import static com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType.EXCHANGE_RATE;

/**
 * An Exchange operation request event representation.
 */
public record ExchangeOperationRequest(UUID contextId,
                                       OperationType operationType,
                                       Metadata metadata,
                                       String callBackURL,
                                       Instant timestamp) implements Event {

    @Override
    public UUID getContextId() {
        return contextId;
    }

    /**
     * Return true if the given request is an exchange request.
     */
    public boolean isExchange() {
        return operationType == EXCHANGE_RATE;
    }

    /**
     * Return true if the given request is a conversion request.
     */
    public boolean isConversion() {
        return operationType == CONVERSION;
    }
}