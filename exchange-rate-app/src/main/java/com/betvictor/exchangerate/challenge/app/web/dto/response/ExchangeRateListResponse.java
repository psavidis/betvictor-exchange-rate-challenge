package com.betvictor.exchangerate.challenge.app.web.dto.response;

import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.util.List;

public record ExchangeRateListResponse(SupportedCurrency currencyFrom,
                                       List<Monetary> monetaryTo) {
}