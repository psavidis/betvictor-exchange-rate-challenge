package com.betvictor.exchangerate.challenge.app.web.dto.request;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.math.BigDecimal;
import java.util.List;

public record ConvertValueListRequest(
        BigDecimal value,
        SupportedCurrency currency,
        List<SupportedCurrency> toCurrencies) {
}