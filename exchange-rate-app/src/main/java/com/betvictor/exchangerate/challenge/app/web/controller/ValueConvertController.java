package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/convert")
public class ValueConvertController {

    @GetMapping("/")
    public ValueConversionResponse getValue(BigDecimal value, String fromCurrency, String toCurrency) {
        throw new UnsupportedOperationException("API is not implemented yet");
    }

    @GetMapping("/to-currencies")
    public ValueConversionListResponse getList(BigDecimal value, String fromCurrency, List<String> toCurrencies) {
        throw new UnsupportedOperationException("API is not implemented yet");
    }

}