package com.betvictor.exchangerate.challenge.client;

import org.springframework.cache.CacheManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Factory that creates {@link DataSourceClient} based on their type.
 */
public class DataSourceClientFactory {

    private DataSourceClientType defaultClientType;
    private Map<DataSourceClientType, DataSourceClient> mappings;

    private DataSourceClientFactory(DataSourceClientType defaultClientType,
                                    Map<DataSourceClientType, DataSourceClient> mappings) {
        this.defaultClientType = defaultClientType;
        this.mappings = mappings;
    }

    public static DataSourceClientFactory of(List<DataSourceClient> clients,
                                             DataSourceClientType defaultClientType) {
        var mappings = new HashMap<DataSourceClientType, DataSourceClient>();
        clients.forEach(c -> mappings.put(c.getType(), c));

        return new DataSourceClientFactory(defaultClientType, mappings);
    }

    public static DataSourceClientFactory of(
            List<DataSourceClient> clients,
            DataSourceClientType defaultClientType,
            CacheManager cacheManager,
            boolean enableCaching) {

        if (enableCaching) {
            var cachedClients = clients.stream()
                    .map(c -> CacheDataSourceClientProxy.of(c, cacheManager))
                    .map(c -> (DataSourceClient) c)
                    .toList();

            return of(cachedClients, defaultClientType);
        }

        return of(clients, defaultClientType);
    }

    public DataSourceClient getClient(Optional<DataSourceClientType> type) {
        var effectiveType = type.orElse(defaultClientType);

        return mappings.get(effectiveType);
    }
}