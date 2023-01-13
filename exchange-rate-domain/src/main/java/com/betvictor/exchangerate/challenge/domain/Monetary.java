package com.betvictor.exchangerate.challenge.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * Value Object that represents money.
 */
public class Monetary {

    private final BigDecimal value;
    private final Currency currency;

    private Monetary(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    /**
     * Static Factory method to create {@link Monetary}.
     *
     * @param value    the arithmetic value of money.
     * @param currency the currency of the money, see <a href="https://en.wikipedia.org/wiki/ISO_4217">...</a>
     * @return the valid monetary
     * @throws NullPointerException     in case of null values
     * @throws IllegalArgumentException in case of negative money values & unrecognised currency
     */
    public static Monetary of(BigDecimal value, SupportedCurrency currency) {
        Objects.requireNonNull(value, "Monetary value cannot  be null");
        Objects.requireNonNull(currency, "Monetary currency cannot  be null");

        if (value.doubleValue() < 0) {
            throw new IllegalArgumentException("Monetary Value cannot be negative");
        }

        var curr = Currency.getInstance(currency.name());

        return new Monetary(value, curr);
    }

    public static Monetary of(BigDecimal value, String currencyIsoCode) {
        return of(value, SupportedCurrency.valueOf(currencyIsoCode.toUpperCase()));
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency.getCurrencyCode();
    }

    @Override
    public String toString() {
        return value + " " + currency.getSymbol();
    }
}