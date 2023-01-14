package com.betvictor.exchangerate.challenge.messaging.dto;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.io.Serializable;

public record ExchangeRequest(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) implements Serializable {

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
