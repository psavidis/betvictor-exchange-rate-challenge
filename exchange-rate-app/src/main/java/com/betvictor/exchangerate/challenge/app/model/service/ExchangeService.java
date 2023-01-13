package com.betvictor.exchangerate.challenge.app.model.service;

import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import com.betvictor.exchangerate.challenge.client.DataSourceClientFactory;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

public class ExchangeService {

    private final DataSourceClientFactory clientFactory;
    private final DataSourceClientType defaultClientType;

    public ExchangeService(DataSourceClientFactory clientFactory, DataSourceClientType defaultClientType) {
        this.clientFactory = clientFactory;
        this.defaultClientType = defaultClientType;
    }

    public ExchangeRateResponse getExchangeRate(ExchangeRateRequest request) {
        return null; //TODO implement
    }

    public ExchangeRateResponse getAllExchangeRates(SupportedCurrency currency) {
        return null; // TODO implement
    }

    public ValueConversionResponse convert(ConvertValueRequest request) {
        return null; // TODO implement
    }

    public ValueConversionListResponse convert(ConvertValueListRequest request) {
        return null; // TODO implement
    }
}
