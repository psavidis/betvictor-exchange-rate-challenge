package com.betvictor.exchangerate.challenge.app.web.dto.response;

import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.util.List;

/**
 * Response DTO for list exchange rate requests that include list results.
 */
public record ExchangeRateListResponse(SupportedCurrency currencyFrom,
                                       List<Monetary> monetaryTo) {
}