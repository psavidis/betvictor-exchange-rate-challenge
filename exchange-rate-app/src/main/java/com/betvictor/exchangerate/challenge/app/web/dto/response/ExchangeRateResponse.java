package com.betvictor.exchangerate.challenge.app.web.dto.response;

import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

/**
 * DTO Response class that contains results of an exchange rate request.
 */
public record ExchangeRateResponse(SupportedCurrency currencyFrom,
                                   Monetary monetaryTo) {
}