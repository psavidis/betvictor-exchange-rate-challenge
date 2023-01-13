package com.betvictor.exchangerate.challenge.app.model.converter;

import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.MonetaryFactory;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExchangeRateApiResponseToExchangeRateListResponseConverter implements Converter<ExchangeRateApiResponse, ExchangeRateListResponse> {

    @Override
    public ExchangeRateListResponse convert(ExchangeRateApiResponse source) {
        var currencyFrom = SupportedCurrency.valueOf(source.base());
        var currencyTo = SupportedCurrency.valueOf(source.base());
        var value = BigDecimal.valueOf(source.rates().get(source.base()));

        source.rates().remove(currencyFrom.name());


        return new ExchangeRateListResponse(
                currencyFrom,
                MonetaryFactory.createListFromRates(source.rates())
        );
    }
}
