package com.betvictor.exchangerate.challenge.client;

import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.util.List;

public class MockDataSourceClient implements DataSourceClient {

    @Override
    public ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
        return null;
    }

    @Override
    public ExchangeRateApiResponse getAllExchangeRates(SupportedCurrency currency) {
        return null;
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
        return DataSourceClientType.EXCHANGE_RATE_HOST;
    }
}
