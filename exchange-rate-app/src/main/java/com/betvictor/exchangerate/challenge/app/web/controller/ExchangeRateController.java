package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {

    private final ExchangeService service;

    public ExchangeRateController(ExchangeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ExchangeRateResponse getExchangeRate(
            @RequestHeader("x-datasource-provider") Optional<DataSourceClientType> type,
            ExchangeRateRequest request
    ) {
        return service.getExchangeRate(request, type);
    }

    @GetMapping("/all")
    public ExchangeRateListResponse getAllExchangeRates(
            @RequestHeader("x-datasource-provider") Optional<DataSourceClientType> type,
            SupportedCurrency currency
    ) {
        return service.getAllExchangeRates(currency, type);
    }

}