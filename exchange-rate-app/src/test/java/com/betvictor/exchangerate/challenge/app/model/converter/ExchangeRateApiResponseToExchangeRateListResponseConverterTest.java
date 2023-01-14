package com.betvictor.exchangerate.challenge.app.model.converter;

import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.EUR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExchangeRateApiResponseToExchangeRateListResponseConverterTest {

    private ExchangeRateApiResponseToExchangeRateListResponseConverter converter = new ExchangeRateApiResponseToExchangeRateListResponseConverter();

    @Test
    void convert_happyPath() {
        var result = converter.convert(new ExchangeRateApiResponse(true, "EUR", new Date(), Map.of(
                "EUR", 1.0,
                "AED", 4.0
        )));

        assertNotNull(result);
        assertEquals(EUR, result.currencyFrom());
        assertEquals(1L, result.monetaryTo().size());
        assertEquals(4.0, result.monetaryTo().get(0).getValue().doubleValue());
        assertEquals("AED", result.monetaryTo().get(0).getCurrency());
    }
}
