package com.betvictor.exchangerate.challenge.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonetaryTest {

    @Test
    void null_value_shouldThrow_NullPointerException() {
        assertThrows(NullPointerException.class, () -> Monetary.of(null, "EUR"));
    }

    @Test
    void null_currency_shouldThrow_NullPointerException() {
        assertThrows(NullPointerException.class, () -> Monetary.of(BigDecimal.valueOf(0), (String) null));
    }

    @Test
    void negativeValue_shouldThrow_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Monetary.of(BigDecimal.valueOf(-1), "EUR"));
    }

    @Test
    void empty_currency_shouldThrow_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Monetary.of(BigDecimal.valueOf(0), ""));
    }

    @Test
    void blank_currency_shouldThrow_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Monetary.of(BigDecimal.valueOf(0), " "));
    }

    @Test
    void invalid_currencyIsoCode_shouldThrow_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Monetary.of(BigDecimal.valueOf(0), "EU"));
    }

    @Test
    void happyPath() {
        var result = Monetary.of(BigDecimal.valueOf(0.0), "EUR");

        assertEquals(BigDecimal.valueOf(0.0), result.getValue());
        assertEquals("EUR", result.getCurrency());
    }

    @Test
    void toString_() {
        var result = Monetary.of(BigDecimal.valueOf(3.14), "EUR");

        assertEquals("3.14 €", result.toString());
    }
}