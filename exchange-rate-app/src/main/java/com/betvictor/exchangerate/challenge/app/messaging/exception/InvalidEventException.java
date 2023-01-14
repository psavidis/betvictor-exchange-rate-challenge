package com.betvictor.exchangerate.challenge.app.messaging.exception;

import com.betvictor.exchangerate.challenge.messaging.event.Event;

/**
 * Exception thrown in case an event is not going to be processed and is considered invalid.
 */
public class InvalidEventException extends RuntimeException {

    private final Event event;

    public InvalidEventException(Event event, String message) {
        super(message);
        this.event = event;
    }

    public Event getEvent() {
        return this.event;
    }
}