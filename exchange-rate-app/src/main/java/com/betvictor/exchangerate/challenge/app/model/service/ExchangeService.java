package com.betvictor.exchangerate.challenge.app.model.service;

import com.betvictor.exchangerate.challenge.app.model.ExchangeContext;
import com.betvictor.exchangerate.challenge.app.model.factory.MonetaryFactory;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import com.betvictor.exchangerate.challenge.client.DataSourceClientFactory;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

public class ExchangeService {

    private final DataSourceClientFactory clientFactory;
    private final ConversionService conversionService;

    public ExchangeService(DataSourceClientFactory clientFactory,
                           ConversionService conversionService) {
        this.clientFactory = clientFactory;
        this.conversionService = conversionService;
    }

    public ExchangeRateResponse getExchangeRate(ExchangeRateRequest request, Optional<DataSourceClientType> type) {
        var client = clientFactory.getClient(type);
        var result = client.getExchangeRate(request.fromCurrency(), request.toCurrency());

        return conversionService.convert(new ExchangeContext(request, result), ExchangeRateResponse.class);
    }

    public ExchangeRateListResponse getAllExchangeRates(SupportedCurrency currency, Optional<DataSourceClientType> type) {
        var client = clientFactory.getClient(type);
        var result = client.getAllExchangeRates(currency);

        return conversionService.convert(result, ExchangeRateListResponse.class);
    }

    public ValueConversionResponse convert(ConvertValueRequest request, Optional<DataSourceClientType> type) {
        var client = clientFactory.getClient(type);
        var monetary = MonetaryFactory.create(request);

        var result = client.convert(monetary, request.toCurrency());

        return conversionService.convert(result, ValueConversionResponse.class);
    }

    public ValueConversionListResponse convert(ConvertValueListRequest request, Optional<DataSourceClientType> type) {
        var client = clientFactory.getClient(type);
        var monetary = MonetaryFactory.create(request);

        var result = client.convert(monetary, request.toCurrencies());

        return conversionService.convert(result, ValueConversionListResponse.class);
    }
}
