package com.betvictor.exchangerate.challenge.client;

import org.springframework.cache.CacheManager;
import org.springframework.retry.support.RetryTemplate;

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

    /**
     * Creates a {@link DataSourceClientFactory} from a list of clients and the default client type
     * to use as a fallback.
     */
    public static DataSourceClientFactory of(List<DataSourceClient> clients,
                                             DataSourceClientType defaultClientType) {
        var mappings = new HashMap<DataSourceClientType, DataSourceClient>();
        clients.forEach(c -> mappings.put(c.getType(), c));

        return new DataSourceClientFactory(defaultClientType, mappings);
    }

    /**
     * Creates a {@link DataSourceClientFactory} with caching & retry options.
     */
    public static DataSourceClientFactory of(
            List<DataSourceClient> clients,
            DataSourceClientType defaultClientType,
            CacheManager cacheManager,
            RetryTemplate retryTemplate,
            boolean enableCaching
    ) {
        var configuredClients = getConfiguredClient(clients, cacheManager, retryTemplate, enableCaching);

        return of(configuredClients, defaultClientType);
    }

    private static List<DataSourceClient> getConfiguredClient(List<DataSourceClient> clients, CacheManager cacheManager, RetryTemplate retryTemplate, boolean enableCaching) {
        return DataSourceClientsBuilder.newBuilder(clients)
                .withCaching(cacheManager, enableCaching)
                .withRetries(retryTemplate, enableCaching)
                .build();
    }

    public DataSourceClient getClient(Optional<DataSourceClientType> type) {
        var effectiveType = type.orElse(defaultClientType);

        return mappings.get(effectiveType);
    }
}