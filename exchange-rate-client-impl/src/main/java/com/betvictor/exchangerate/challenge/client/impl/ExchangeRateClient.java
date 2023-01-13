package com.betvictor.exchangerate.challenge.client.impl;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import com.betvictor.exchangerate.challenge.client.DataSourceClient;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.client.ExchangeRateApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeRateClient implements DataSourceClient {

    private static final String API_URL = "https://api.exchangerate.host/latest";

    private final RestTemplate template;

    public ExchangeRateClient(RestTemplate template) {
        this.template = template;
    }

    @Override
    public ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
        return null; //TODO implement
    }

    @Override
    public ExchangeRateApiResponse getAllExchangeRates() {
        return template.getForObject(API_URL, ExchangeRateApiResponse.class);
    }

    @Override
    public DataSourceClientType getType() {
        return DataSourceClientType.EXCHANGE_RATE_HOST;
    }
}
