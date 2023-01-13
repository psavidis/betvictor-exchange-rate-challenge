package com.betvictor.exchangerate.challenge.app.web.dto.response;

import com.betvictor.exchangerate.challenge.domain.Monetary;

/**
 * Response DTO for Value Conversion Single Operation.
 */
public record ValueConversionResponse(Monetary monetary, Monetary conversion) {
}