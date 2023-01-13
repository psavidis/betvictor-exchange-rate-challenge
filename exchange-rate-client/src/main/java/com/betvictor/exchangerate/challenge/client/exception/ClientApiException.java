package com.betvictor.exchangerate.challenge.client.exception;

/**
 * Exception thrown during an Api call of {@link com.betvictor.exchangerate.challenge.client.DataSourceClient}.
 */
public class ClientApiException extends RuntimeException {

    public ClientApiException(String message) {
        super(message);
    }

    public ClientApiException(String message, Exception cause) {
        super(message, cause);
    }
}
