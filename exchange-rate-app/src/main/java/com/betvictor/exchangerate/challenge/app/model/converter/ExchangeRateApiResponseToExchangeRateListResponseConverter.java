package com.betvictor.exchangerate.challenge.app.model.converter;

import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateListResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.MonetaryFactory;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ExchangeRateApiResponseToExchangeRateListResponseConverter implements Converter<ExchangeRateApiResponse, ExchangeRateListResponse> {

    @Override
    public ExchangeRateListResponse convert(ExchangeRateApiResponse source) {
        var currencyFrom = SupportedCurrency.valueOf(source.base());

        var rates = new HashMap<>(source.rates());
        rates.entrySet().removeIf(e -> currencyFrom.name().equals(e.getKey()));

        return new ExchangeRateListResponse(
                currencyFrom,
                MonetaryFactory.createListFromRates(rates)
        );
    }
}
