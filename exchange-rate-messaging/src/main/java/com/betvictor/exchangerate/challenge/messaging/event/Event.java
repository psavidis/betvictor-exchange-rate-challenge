package com.betvictor.exchangerate.challenge.messaging.event;

import java.io.Serializable;
import java.util.UUID;

/**
 * Interface to be implemented by all events.
 */
public interface Event extends Serializable {

    /**
     * The correlation identifier of this event.
     */
    UUID getContextId();
}