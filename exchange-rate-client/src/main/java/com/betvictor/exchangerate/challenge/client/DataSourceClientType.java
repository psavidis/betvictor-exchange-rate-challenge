package com.betvictor.exchangerate.challenge.client;

/**
 * Enumeration containing all {@link DataSourceClient}ce implementation types.
 */
public enum DataSourceClientType {
    EXCHANGE_RATE_HOST,
    FREE_CURRENCY_API;

    public static DataSourceClientType getDefault() {
        return EXCHANGE_RATE_HOST;
    }
}
