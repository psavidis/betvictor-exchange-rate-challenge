package com.betvictor.exchangerate.challenge.messaging.dto.request;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO to pass data around for Conversion requests.
 */
public record ConversionRequest(BigDecimal value,
                                SupportedCurrency currency,
                                List<SupportedCurrency> toCurrencies) implements Serializable {

    public boolean isList() {
        return toCurrencies != null && toCurrencies.size() > 1;
    }
}
