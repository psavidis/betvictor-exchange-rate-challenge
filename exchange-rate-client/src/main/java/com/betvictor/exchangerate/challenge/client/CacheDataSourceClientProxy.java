package com.betvictor.exchangerate.challenge.client;

import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.math.BigDecimal;
import java.util.*;

/**
 * Cache-Aside Proxy for {@link DataSourceClient}.
 * <p>
 * Uses only getAllExchangeRates to reduces the number of queries against the APIs
 * to calculate all the rest of operations.
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
        var response = getAllExchangeRates(fromCurrency);

        var rates = response.rates();
        var toCurrencyName = toCurrency.name();
        var newRates = Map.of(
                toCurrencyName, rates.get(toCurrencyName)
        );

        return new ExchangeRateApiResponse(
                response.success(),
                response.base(),
                response.date(),
                newRates
        );
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

    @Override
    public ConversionResponse convert(Monetary monetary, SupportedCurrency toCurrency) {
        return convert(monetary, List.of(toCurrency));
    }

    @Override
    public ConversionResponse convert(Monetary monetary, List<SupportedCurrency> toCurrencies) {
        var currency = SupportedCurrency.valueOf(monetary.getCurrency());
        var rateResponse = getAllExchangeRates(currency);
        var conversions = getConversions(monetary, toCurrencies, rateResponse);

        return new ConversionResponse(monetary, conversions);
    }

    @Override
    public DataSourceClientType getType() {
        return delegate.getType();
    }

    private String getCacheKey(SupportedCurrency currency) {
        return CACHE_NAME.replace(CACHE_NAME_CURRENCY_PARAM, currency.name());
    }

    private List<Monetary> getConversions(Monetary monetary, List<SupportedCurrency> toCurrencies, ExchangeRateApiResponse response) {
        var rates = new HashMap<>(response.rates());
        rates.remove(monetary.getCurrency());

        var results = new ArrayList<Monetary>();
        rates.forEach((currency, rate) -> {
            var cur = SupportedCurrency.valueOf(currency);
            if (!toCurrencies.contains(cur)) {
                return;
            }

            var value = monetary.getValue().multiply(BigDecimal.valueOf(rate));

            results.add(Monetary.of(
                    value, SupportedCurrency.valueOf(currency)
            ));
        });
        return results;
    }
}