package com.betvictor.exchangerate.challenge.app.messaging.endpoint;

import com.betvictor.exchangerate.challenge.messaging.ExchangeOperationRequestEndpoint;
import com.betvictor.exchangerate.challenge.messaging.event.ExchangeOperationRequest;
import org.springframework.stereotype.Component;

@Component
public class ExchangeOperationRequestEndpointImpl implements ExchangeOperationRequestEndpoint {

    private final ExchangeOperationRequestHandler handler;

    public ExchangeOperationRequestEndpointImpl(ExchangeOperationRequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onEvent(ExchangeOperationRequest event) {
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

}