package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
public class ValueConvertController {

    private final ExchangeService service;

    public ValueConvertController(ExchangeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ValueConversionResponse convertToCurrency(ConvertValueRequest request) {
        return service.convert(request);
    }

    @GetMapping("/to-currencies")
    public ValueConversionListResponse convertToCurrencies(ConvertValueListRequest request) {
        return service.convert(request);
    }

}