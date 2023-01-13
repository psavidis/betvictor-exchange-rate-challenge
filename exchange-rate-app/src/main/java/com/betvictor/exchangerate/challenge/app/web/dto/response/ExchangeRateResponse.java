package com.betvictor.exchangerate.challenge.app.web.dto.response;

import java.math.BigDecimal;

public record ExchangeRateResponse(String currencyFrom,
                                   String currencyTo,
                                   BigDecimal resultValue) {
}
