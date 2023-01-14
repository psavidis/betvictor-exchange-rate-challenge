package com.betvictor.exchangerate.challenge.app.web.dto.request;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

/**
 * DTO Request class to get the exchange rate from a currency into another currency.
 */
public record ExchangeRateRequest(
        SupportedCurrency fromCurrency,
        SupportedCurrency toCurrency) {
}
