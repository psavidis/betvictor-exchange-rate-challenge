package com.betvictor.exchangerate.challenge.messaging.dto;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record ConversionRequest(BigDecimal value,
                                SupportedCurrency currency,
                                List<SupportedCurrency> toCurrencies) implements Serializable {

    boolean isSingular() {
        return toCurrencies.size() == 1;
    }

    public boolean isList() {
        return toCurrencies != null && toCurrencies.size() > 1;
    }
}
