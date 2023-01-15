package com.betvictor.exchangerate.challenge.messaging.dto.request;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.io.Serializable;

/**
 * DTO representing an Exchange Request.
 */
public record ExchangeRequest(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) implements Serializable {

    /**
     * Returns true if the operation is to fetch all results.
     */
    public boolean isAll() {
        return toCurrency == null;
    }

    public SupportedCurrency getFromCurrency() {
        return fromCurrency;
    }

    public SupportedCurrency getToCurrency() {
        return toCurrency;
    }
}
