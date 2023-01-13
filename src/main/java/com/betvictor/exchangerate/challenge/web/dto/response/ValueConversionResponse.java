package com.betvictor.exchangerate.challenge.web.dto.response;

import java.math.BigDecimal;

/**
 * Response DTO for Value Conversion Single Operation.
 */
public record ValueConversionResponse(
        BigDecimal value,
        String currency
) {
}