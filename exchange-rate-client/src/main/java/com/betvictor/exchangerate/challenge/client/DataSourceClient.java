package com.betvictor.exchangerate.challenge.client;

import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.util.List;

/**
 * Interface that any Data source provider can implement to provide their own exchange rates.
 */
public interface DataSourceClient {

    ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency);
    ExchangeRateApiResponse getAllExchangeRates(SupportedCurrency currency);

    ConversionResponse convert(Monetary monetary, SupportedCurrency toCurrency);
    ConversionResponse convert(Monetary monetary, List<SupportedCurrency> toCurrencies);

    DataSourceClientType getType();
}