package com.betvictor.exchangerate.challenge.app.web.dto.response;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Response DTO for Value Conversion List Operation.
 */
public record ValueConversionListResponse(
        BigDecimal value,
        String currency,
        Map<String, BigDecimal> conversions) {
}
