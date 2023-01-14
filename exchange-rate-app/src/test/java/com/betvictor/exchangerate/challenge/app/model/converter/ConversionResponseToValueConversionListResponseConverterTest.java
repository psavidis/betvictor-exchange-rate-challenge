package com.betvictor.exchangerate.challenge.app.model.converter;

import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.EUR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConversionResponseToValueConversionListResponseConverterTest {

    private ConversionResponseToValueConversionListResponseConverter converter = new ConversionResponseToValueConversionListResponseConverter();

    @Test
    void test_happyPath() {
        var monetary = Monetary.of(BigDecimal.valueOf(1.0), EUR);
        var conversions = List.of(monetary);

        var result = converter.convert(new ConversionResponse(monetary, conversions));

        assertNotNull(result);
        assertEquals(monetary, result.monetary());
        assertEquals(conversions, result.conversions());
    }

}
