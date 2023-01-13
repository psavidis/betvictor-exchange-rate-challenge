package com.betvictor.exchangerate.challenge.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Factory that creates {@link DataSourceClient} based on their type.
 */
public class DataSourceClientFactory {

    private final DataSourceClientType defaultClientType;
    private final Map<DataSourceClientType, DataSourceClient> mappings = new HashMap<>();

    public DataSourceClientFactory(List<DataSourceClient> clients, DataSourceClientType defaultClientType) {
        this.defaultClientType = defaultClientType;
        clients.forEach(c -> mappings.put(c.getType(), c));
    }

    public DataSourceClient getClient(Optional<DataSourceClientType> type) {
        var effectiveType = type.orElse(defaultClientType);

        return mappings.get(effectiveType);
    }
}