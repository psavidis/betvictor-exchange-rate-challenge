package com.betvictor.exchangerate.challenge.app.web.dto.request;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.math.BigDecimal;

/**
 * DTO class to convert a value of a currency into another currency.
 */
public record ConvertValueRequest(
        BigDecimal value,
        SupportedCurrency currency,
        SupportedCurrency toCurrency) {
}