package com.betvictor.exchangerate.challenge.app.web.dto.request;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

public record ExchangeRateRequest(
        SupportedCurrency fromCurrency,
        SupportedCurrency toCurrency) {
}
