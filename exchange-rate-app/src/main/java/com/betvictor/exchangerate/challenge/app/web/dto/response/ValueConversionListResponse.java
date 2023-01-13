package com.betvictor.exchangerate.challenge.app.web.dto.response;

import com.betvictor.exchangerate.challenge.domain.Monetary;

import java.util.List;

/**
 * Response DTO for Value Conversion List Operation.
 */
public record ValueConversionListResponse(
        Monetary monetary,
        List<Monetary> conversions) {
}
