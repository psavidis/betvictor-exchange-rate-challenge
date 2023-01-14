package com.betvictor.exchangerate.challenge.app.model;

import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;

/**
 * Dto to carry data around for {@link com.betvictor.exchangerate.challenge.app.model.converter.ExchangeContextToExchangeRateResponseConverter}.
 */
public record ExchangeContext(ExchangeRateRequest request,
                              ExchangeRateApiResponse response) {
}