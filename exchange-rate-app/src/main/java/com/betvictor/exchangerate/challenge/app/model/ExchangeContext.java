package com.betvictor.exchangerate.challenge.app.model;

import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;

public record ExchangeContext(ExchangeRateRequest request,
                              ExchangeRateApiResponse response) {
}