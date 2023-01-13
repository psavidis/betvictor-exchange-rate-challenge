package com.betvictor.exchangerate.challenge.client.response;

import com.betvictor.exchangerate.challenge.domain.Monetary;

import java.util.List;

public record ConversionResponse(Monetary monetary, List<Monetary> conversions) {
}