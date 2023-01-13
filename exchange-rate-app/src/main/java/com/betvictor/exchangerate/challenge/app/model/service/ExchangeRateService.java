package com.betvictor.exchangerate.challenge.app.model.service;

import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.client.DataSourceClientFactory;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

public class ExchangeRateService {

    private final DataSourceClientFactory clientFactory;
    private final DataSourceClientType defaultClientType;

    public ExchangeRateService(DataSourceClientFactory clientFactory, DataSourceClientType defaultClientType) {
        this.clientFactory = clientFactory;
        this.defaultClientType = defaultClientType;
    }

    public ExchangeRateResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
        return null; //TODO implement
    }

    public ExchangeRateResponse getAllExchangeRates(SupportedCurrency currency) {
        return null; // TODO implement
    }
}
