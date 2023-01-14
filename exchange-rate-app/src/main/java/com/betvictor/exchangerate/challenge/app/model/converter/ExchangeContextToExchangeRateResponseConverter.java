package com.betvictor.exchangerate.challenge.app.model.converter;

import com.betvictor.exchangerate.challenge.app.model.ExchangeContext;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExchangeContextToExchangeRateResponseConverter implements Converter<ExchangeContext, ExchangeRateResponse> {

    @Override
    public ExchangeRateResponse convert(ExchangeContext source) {
        var response = source.response();
        var currencyFrom = SupportedCurrency.valueOf(response.base());
        var currencyTo = source.request().toCurrency();

        var val = response.rates().get(currencyTo.name());
        var value = val != null ? BigDecimal.valueOf(val) : BigDecimal.valueOf(-1);

        return new ExchangeRateResponse(
                currencyFrom,
                Monetary.of(value, currencyTo)
        );
    }
}
