package com.betvictor.exchangerate.challenge.client;

import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.Objects;

/**
 * Cache-Aside Proxy for {@link DataSourceClient}.
 */
public class CacheDataSourceClientProxy implements DataSourceClient {

    private static final String CACHE_NAME_CURRENCY_PARAM = "{currency}";
    private static final String CACHE_NAME = "getAllExchangeRates_" + CACHE_NAME_CURRENCY_PARAM;

    private final DataSourceClient delegate;
    private final Cache cache;

    private CacheDataSourceClientProxy(DataSourceClient delegate, Cache cache) {
        this.delegate = delegate;
        this.cache = cache;
    }

    public static CacheDataSourceClientProxy of(DataSourceClient delegate, CacheManager cacheManager) {
        var cacheName = delegate.getType().name();
        var cache = Objects.requireNonNull(cacheManager.getCache(cacheName), "Failed to fetch cache");

        return new CacheDataSourceClientProxy(delegate, cache);
    }

    @Override
    public ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
        return null;
    }

    @Override
    public ExchangeRateApiResponse getAllExchangeRates(SupportedCurrency currency) {
        var key = getCacheKey(currency);
        var result = cache.get(key);

        if (result == null) {
            var newResult = delegate.getAllExchangeRates(currency);
            cache.put(key, newResult);
            return newResult;
        }

        return (ExchangeRateApiResponse) result.get();
    }

    private String getCacheKey(SupportedCurrency currency) {
        return CACHE_NAME.replace(CACHE_NAME_CURRENCY_PARAM, currency.name());
    }

    @Override
    public ConversionResponse convert(Monetary monetary, SupportedCurrency toCurrency) {
        return null;
    }

    @Override
    public ConversionResponse convert(Monetary monetary, List<SupportedCurrency> toCurrencies) {
        return null;
    }

    @Override
    public DataSourceClientType getType() {
        return delegate.getType();
    }
}