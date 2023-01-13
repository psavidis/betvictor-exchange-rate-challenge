package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeRateService;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {

    private final ExchangeRateService service;

    public ExchangeRateController(ExchangeRateService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ExchangeRateResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
        return service.getExchangeRate(fromCurrency, toCurrency);
    }

    @GetMapping("/all")
    public ExchangeRateResponse getAllExchangeRates(SupportedCurrency currency) {
        return service.getAllExchangeRates(currency);
    }

}