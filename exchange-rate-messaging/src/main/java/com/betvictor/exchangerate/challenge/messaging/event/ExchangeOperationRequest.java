package com.betvictor.exchangerate.challenge.messaging.event;

import com.betvictor.exchangerate.challenge.messaging.Metadata;
import com.betvictor.exchangerate.challenge.messaging.OperationType;

import java.util.UUID;

import static com.betvictor.exchangerate.challenge.messaging.OperationType.CONVERSION;
import static com.betvictor.exchangerate.challenge.messaging.OperationType.EXCHANGE_RATE;

public record ExchangeOperationRequest (UUID contextId,
                                        OperationType operationType,
                                        Metadata metadata,
                                        String callBackURL) implements Event {

    @Override
    public UUID getContextId() {
        return contextId;
    }

    public boolean isExchange() {
        return operationType == EXCHANGE_RATE;
    }

    public boolean isConversion() {
        return operationType == CONVERSION;
    }
}