package com.betvictor.exchangerate.challenge.client.impl;

import com.betvictor.exchangerate.challenge.client.exception.ClientApiException;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class ApiResponses {

    public static ExchangeRateApiResponse tryFetchResponse(RestTemplate template, String url) {
        try {
            var result = Optional.ofNullable(
                    template.getForObject(url, ExchangeRateApiResponse.class)
            );

            var response = result.orElseThrow(() -> new ClientApiException("Response was null"));

            if (!response.success()) {
                throw new ClientApiException("Response was Unsuccessful");
            }

            return response;
        } catch (Exception e) {
            throw new ClientApiException("Failed to fetch response", e);
        }
    }

}
