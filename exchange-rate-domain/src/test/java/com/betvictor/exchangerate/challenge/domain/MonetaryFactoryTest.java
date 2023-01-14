package com.betvictor.exchangerate.challenge.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.EUR;
import static org.junit.jupiter.api.Assertions.*;

public class MonetaryFactoryTest {

    @Test
    void null_rates_shouldThrow_NullPointerException() {
        assertThrows(NullPointerException.class, () -> MonetaryFactory.createListFromRates(null));
    }

    @Test
    void emptyMap_shouldReturn_emptyList() {
        var result = MonetaryFactory.createListFromRates(Collections.emptyMap());

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void happyPath() {
        var result = MonetaryFactory.createListFromRates(Map.of("EUR", 1.0));

        assertEquals(Monetary.of(BigDecimal.valueOf(1.0), EUR), result.get(0));
    }
}
