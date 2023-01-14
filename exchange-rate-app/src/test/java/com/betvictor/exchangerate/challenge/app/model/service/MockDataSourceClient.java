package com.betvictor.exchangerate.challenge.app.model.service;

import com.betvictor.exchangerate.challenge.client.DataSourceClient;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MockDataSourceClient implements DataSourceClient {

    private static final ExchangeRateApiResponse DUMMY_EX = new ExchangeRateApiResponse(true, "EUR", new Date(), new HashMap<>());
    private static final ConversionResponse DUMMY_CON = new ConversionResponse(Monetary.of(BigDecimal.valueOf(1.0), SupportedCurrency.EUR), List.of());

    @Override
    public ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
        return DUMMY_EX;
    }

    @Override
    public ExchangeRateApiResponse getAllExchangeRates(SupportedCurrency currency) {
        return DUMMY_EX;
    }

    @Override
    public ConversionResponse convert(Monetary monetary, SupportedCurrency toCurrency) {
        return DUMMY_CON;
    }

    @Override
    public ConversionResponse convert(Monetary monetary, List<SupportedCurrency> toCurrencies) {
        return DUMMY_CON;
    }

    @Override
    public DataSourceClientType getType() {
        return DataSourceClientType.EXCHANGE_RATE_HOST;
    }
}



