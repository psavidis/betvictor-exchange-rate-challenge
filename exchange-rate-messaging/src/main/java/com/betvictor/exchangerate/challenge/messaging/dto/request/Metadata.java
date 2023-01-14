package com.betvictor.exchangerate.challenge.messaging.dto.request;

import com.betvictor.exchangerate.challenge.client.DataSourceClientType;

import java.io.Serializable;

/**
 * Record class which passes around the Request for exchange / convert worfklows.
 */
public record Metadata(DataSourceClientType datasourceProvider, Object request)
        implements Serializable {
}
