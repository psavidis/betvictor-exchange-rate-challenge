package com.betvictor.exchangerate.challenge.app.web.dto.response;

import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

public record ExchangeRateResponse(SupportedCurrency currencyFrom,
                                   Monetary monetaryTo) {
}