package com.betvictor.exchangerate.challenge.client;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory that creates {@link DataSourceClient} based on their type.
 */
@Component
public class DataSourceClientFactory {

    private Map<DataSourceClientType, DataSourceClient> mappings = new HashMap<>();

    public DataSourceClientFactory(List<DataSourceClient> clients) {
        clients.forEach(c -> mappings.put(c.getType(), c));
    }

    public DataSourceClient create(DataSourceClientType provider) {
        return mappings.get(provider);
    }
}