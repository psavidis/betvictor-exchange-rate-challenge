package com.betvictor.exchangerate.challenge.messaging;


import com.betvictor.exchangerate.challenge.messaging.event.ExchangeOperationRequest;

/**
 * Endpoint called when a new {@link ExchangeOperationRequest} takes place.
 */
public interface ExchangeOperationRequestEndpoint {

    /**
     * Method called when the event occurs.
     */
    void onEvent(ExchangeOperationRequest event);
}
