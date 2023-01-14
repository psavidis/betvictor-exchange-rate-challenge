package com.betvictor.exchangerate.challenge.app.model.factory;

import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.*;
import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonetaryFactoryTest {

    @Test
    void create_convertValueRequest_happyPath() {
        var result = MonetaryFactory.create(new ConvertValueRequest(TEN, EUR, AED));

        assertEquals("EUR", result.getCurrency());
        assertEquals(10.0, result.getValue().doubleValue());
    }

    @Test
    void create_ConvertValueListRequest_happyPath() {
        var result = MonetaryFactory.create(new ConvertValueListRequest(TEN, EUR, List.of(ANG)));

        assertEquals("EUR", result.getCurrency());
        assertEquals(10.0, result.getValue().doubleValue());
    }
}
