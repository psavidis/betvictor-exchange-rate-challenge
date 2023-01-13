package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {

    private final ExchangeService service;

    public ExchangeRateController(ExchangeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ExchangeRateResponse getExchangeRate(ExchangeRateRequest request) {
        return service.getExchangeRate(request);
    }

    @GetMapping("/all")
    public ExchangeRateResponse getAllExchangeRates(SupportedCurrency currency) {
        return service.getAllExchangeRates(currency);
    }

}