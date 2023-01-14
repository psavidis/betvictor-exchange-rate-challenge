package com.betvictor.exchangerate.challenge.client.impl;

import com.betvictor.exchangerate.challenge.client.DataSourceClient;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.client.exception.ClientApiException;
import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.MonetaryFactory;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ExchangeRateClient implements DataSourceClient {

    private static final String API_URL = "https://api.exchangerate.host/latest";

    private RestTemplate template;

    public ExchangeRateClient(RestTemplate template) {
        this.template = template;
    }

    @Override
    public ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
        return tryFetchResponse(API_URL + "?base=" + fromCurrency + "&symbols" + toCurrency);
    }

    @Override
    public ExchangeRateApiResponse getAllExchangeRates(SupportedCurrency currency) {
        return tryFetchResponse(API_URL + "?base=" + currency);
    }

    @Override
    public ConversionResponse convert(Monetary monetary, SupportedCurrency toCurrency) {
        return convert(monetary, List.of(toCurrency));
    }

    @Override
    public ConversionResponse convert(Monetary monetary, List<SupportedCurrency> toCurrencies) {
        var commaSeparatedSymbols = toCurrencies.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));

        var response = tryFetchResponse(API_URL + "?base=" + monetary.getCurrency() + "&symbols=" + commaSeparatedSymbols);

        var rates = response.rates();
        var conversions = MonetaryFactory.createListFromRates(rates);

        return new ConversionResponse(monetary, conversions);
    }

    @Override
    public DataSourceClientType getType() {
        return DataSourceClientType.EXCHANGE_RATE_HOST;
    }

    private ExchangeRateApiResponse tryFetchResponse(String url) {
        try {
            var result = Optional.ofNullable(
                    template.getForObject(url, ExchangeRateApiResponse.class)
            );

            var response = result.orElseThrow(() -> new ClientApiException("Response was null"));

            if (!response.success()) {
                throw new ClientApiException("Response was Unsuccessful");
            }

            return response;
        } catch (Exception e) {
            throw new ClientApiException("Failed to fetch response", e);
        }
    }
}
