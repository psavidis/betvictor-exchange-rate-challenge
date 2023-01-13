package com.betvictor.exchangerate.challenge.client;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;

/**
 * Response DTO of Exchange API.
 * <p>
 * API Documentation here: <a href="https://exchangerate.host/#/">...</a>
 */
public record ExchangeRateApiResponse(
        Boolean success,
        String base,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") Date date,
        Map<String, Double> rates) {
}