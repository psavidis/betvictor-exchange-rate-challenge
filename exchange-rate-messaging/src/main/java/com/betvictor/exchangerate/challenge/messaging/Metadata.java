package com.betvictor.exchangerate.challenge.messaging;

import com.betvictor.exchangerate.challenge.client.DataSourceClientType;

import java.io.Serializable;

public record Metadata(DataSourceClientType datasourceProvider, Object request)
        implements Serializable {
}
