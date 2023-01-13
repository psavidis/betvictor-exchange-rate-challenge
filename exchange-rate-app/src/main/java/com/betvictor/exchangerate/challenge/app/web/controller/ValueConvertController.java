package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
public class ValueConvertController {

    private final ExchangeService service;

    public ValueConvertController(ExchangeService service) {
        this.service = service;
    }

    @GetMapping("/convert-to-currency")
    public ValueConversionResponse convertToCurrency(
            @RequestHeader("x-datasource-provider") Optional<DataSourceClientType> type,
            ConvertValueRequest request
    ) {
        return service.convert(request, type);
    }

    @GetMapping("/convert-to-currencies")
    public ValueConversionListResponse convertToCurrencies(
            @RequestHeader("x-datasource-provider") Optional<DataSourceClientType> type,
            ConvertValueListRequest request
    ) {
        return service.convert(request, type);
    }

}