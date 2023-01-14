package com.betvictor.exchangerate.challenge.client.impl;

import com.betvictor.exchangerate.challenge.client.DataSourceClient;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Component
public class FreeCurrencyApiClient implements DataSourceClient {

    private static final String API_URL = "https://api.freecurrencyapi.com/v1/latest";

    private RestTemplate template;
    private String apiKey;

    public FreeCurrencyApiClient(RestTemplate template,
                                 @Value("${application.datasource-provider.free-currency-api.api-key}") String apiKey) {
        this.template = template;
        this.apiKey = apiKey;
    }

    @Override
    public ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
        var url = API_URL + "?apikey=" + apiKey
                + "&base_currency=" + fromCurrency.name()
                + "&currencies=" + fromCurrency.name() + "," + toCurrency.name();

        var result = template.getForObject(url, Map.class);

        return new ExchangeRateApiResponse(
                true,
                fromCurrency.name(),
                Date.from(Instant.now()),
                (Map<String, Double>) result.get("data")
        );
    }

    @Override
    public ExchangeRateApiResponse getAllExchangeRates(SupportedCurrency currency) {
        var url = API_URL
                + "?apikey=" + apiKey
                + "&base_currency=" + currency.name()
                + "&currencies=";

        var result = template.getForObject(url, Map.class);

        return new ExchangeRateApiResponse(
                true,
                currency.name(),
                Date.from(Instant.now()),
                (Map<String, Double>) result.get("data")
        );
    }

    @Override
    public ConversionResponse convert(Monetary monetary, SupportedCurrency toCurrency) {
        var response = getExchangeRate(SupportedCurrency.valueOf(monetary.getCurrency()), toCurrency);
        var rate = response.rates().get(toCurrency.name());
        var value = monetary.getValue().multiply(BigDecimal.valueOf(rate));

        return new ConversionResponse(
                monetary,
                List.of(Monetary.of(value, toCurrency))
        );
    }

    @Override
    public ConversionResponse convert(Monetary monetary, List<SupportedCurrency> toCurrencies) {
        var response = getAllExchangeRates(SupportedCurrency.valueOf(monetary.getCurrency()));
        var conversions = new ArrayList<Monetary>();

        var rates = new HashMap<>(response.rates());
        rates.remove(monetary.getCurrency());

        rates.forEach((currency, rate) -> {
            var cur = SupportedCurrency.valueOf(currency);
            if (!toCurrencies.contains(cur)) {
                return;
            }

            var value = monetary.getValue().multiply(BigDecimal.valueOf(rate));

            conversions.add(Monetary.of(
                    value, SupportedCurrency.valueOf(currency)
            ));
        });

        return new ConversionResponse(
                monetary,
                conversions
        );
    }

    @Override
    public DataSourceClientType getType() {
        return DataSourceClientType.FREE_CURRENCY_API;
    }
}
