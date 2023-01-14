package com.betvictor.exchangerate.challenge.app.model.converter;

import com.betvictor.exchangerate.challenge.app.model.ExchangeContext;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.AED;
import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.EUR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExchangeContextToExchangeRateResponseConverterTest {

    private ExchangeContextToExchangeRateResponseConverter converter = new ExchangeContextToExchangeRateResponseConverter();

    @Test
    void convert_happyPath() {
        var result = converter.convert(new ExchangeContext(
                new ExchangeRateRequest(EUR, AED),
                new ExchangeRateApiResponse(false, "EUR", new Date(), Map.of("EUR", 1.0, "AED", 2.0))
        ));

        assertNotNull(result);
        assertEquals(EUR, result.currencyFrom());
        assertEquals("AED", result.monetaryTo().getCurrency());
        assertEquals(2.0, result.monetaryTo().getValue().doubleValue());
    }
}
