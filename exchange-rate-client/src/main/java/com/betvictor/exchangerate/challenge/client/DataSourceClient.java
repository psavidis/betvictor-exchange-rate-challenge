package com.betvictor.exchangerate.challenge.client;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

/**
 * Interface that any Data source provider can implement to provide their own exchange rates.
 */
public interface DataSourceClient {

    ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency);

    ExchangeRateApiResponse getAllExchangeRates();

    DataSourceClientType getType();
}
