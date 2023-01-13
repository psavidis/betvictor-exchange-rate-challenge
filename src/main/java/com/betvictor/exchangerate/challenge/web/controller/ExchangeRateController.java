package com.betvictor.exchangerate.challenge.web.controller;

import com.betvictor.exchangerate.challenge.web.dto.response.ExchangeRateResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {

    @GetMapping("/")
    public ExchangeRateResponse getExchangeRate(String fromCurrency, String toCurrency) {
        throw new UnsupportedOperationException("API is not implemented yet");
    }

    @GetMapping("/all")
    public ExchangeRateResponse getAllExchangeRates(String currency) {
        throw new UnsupportedOperationException("API is not implemented yet");
    }

}