package com.betvictor.exchangerate.challenge.client;

/**
 * Enumeration containing all {@link DataSourceClient}ce implementation types.
 */
public enum DataSourceClientType {
    EXCHANGE_RATE_HOST,
    FIXER_IO;

    public static DataSourceClientType getDefault() {
        return EXCHANGE_RATE_HOST;
    }
}
