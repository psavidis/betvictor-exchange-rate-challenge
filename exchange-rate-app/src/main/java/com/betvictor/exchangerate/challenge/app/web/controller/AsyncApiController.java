package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.common.condition.AsynApiEnabledCondition;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import com.betvictor.exchangerate.challenge.messaging.service.MessagingService;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Conditional(AsynApiEnabledCondition.class)
@RestController
@RequestMapping("/async")
public class AsyncApiController {

    private final MessagingService service;

    public AsyncApiController(MessagingService service) {
        this.service = service;
    }

    @GetMapping("/get-all-exchange-rates")
    public ResponseEntity<?> getAllExchangeRates(
            @RequestHeader("x-datasource-provider") Optional<DataSourceClientType> type,
            SupportedCurrency currency,
            String callBackURL
    ) {
        service.sendExchangeRequest(type, currency, null, callBackURL);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/get-exchange-rate")
    public ResponseEntity<?> getExchangeRate(
            @RequestHeader("x-datasource-provider") Optional<DataSourceClientType> type,
            ExchangeRateRequest request,
            String callBackURL
    ) {
        service.sendExchangeRequest(type, request.fromCurrency(), request.toCurrency(), callBackURL);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/convert-to-currency")
    public ResponseEntity<?> convertToCurrency(
            @RequestHeader("x-datasource-provider") Optional<DataSourceClientType> type,
            ConvertValueRequest request,
            String callBackURL
    ) {
        service.sendConversionRequest(
                type,
                request.value(),
                request.currency(),
                List.of(request.toCurrency()),
                callBackURL
        );

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/convert-to-currencies")
    public ResponseEntity<?> convertToCurrencies(
            @RequestHeader("x-datasource-provider") Optional<DataSourceClientType> type,
            ConvertValueListRequest request,
            String callBackURL
    ) {
        service.sendConversionRequest(
                type,
                request.value(),
                request.currency(),
                request.toCurrencies(),
                callBackURL
        );
        return ResponseEntity.accepted().build();
    }

}