package com.betvictor.exchangerate.challenge.client;

import org.springframework.cache.CacheManager;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;

/**
 * Builder that Simplifies the Proxying & Decoration of all clients according to the given
 * configuration.
 */
public class DataSourceClientsBuilder {

    private RetryTemplate retryTemplate;
    private CacheManager cacheManager;
    private List<DataSourceClient> clients;
    private boolean cachingEnabled;
    private boolean retriesEnabled;

    private DataSourceClientsBuilder(List<DataSourceClient> clients) {
        this.clients = clients;
    }

    public DataSourceClientsBuilder withCaching(CacheManager cacheManager, boolean enabled) {
        this.cacheManager = cacheManager;
        this.cachingEnabled = enabled;
        return this;
    }

    public DataSourceClientsBuilder withRetries(RetryTemplate retryTemplate, boolean retriesEnabled) {
        this.retryTemplate = retryTemplate;
        this.retriesEnabled = retriesEnabled;
        return this;
    }

    public List<DataSourceClient> build() {
        if (cachingEnabled) {
            clients = getCachedClients(cacheManager);
        }

        if (retriesEnabled) {
            clients = getRetryClients(clients);
        }

        return clients;
    }

    private List<DataSourceClient> getRetryClients(List<DataSourceClient> clients) {
        return clients.stream()
                .map(c -> RetryDataSourceClientDecorator.of(c, retryTemplate))
                .map(c -> (DataSourceClient) c)
                .toList();
    }

    private List<DataSourceClient> getCachedClients(CacheManager cacheManager) {
        return clients.stream()
                .map(c -> CacheDataSourceClientProxy.of(c, cacheManager))
                .map(c -> (DataSourceClient) c)
                .toList();
    }

    public static DataSourceClientsBuilder newBuilder(List<DataSourceClient> clients) {
        return new DataSourceClientsBuilder(clients);
    }
}