package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateControllerTest {

    @Mock
    private ExchangeService service;

    @InjectMocks
    private ExchangeRateController controller;

    @Test
    void getExchangeRate_happyPath() {
        var request = new ExchangeRateRequest(SupportedCurrency.EUR, SupportedCurrency.AED);
        var type = Optional.of(DataSourceClientType.EXCHANGE_RATE_HOST);

        controller.getExchangeRate(type, request);

        verify(service, times(1)).getExchangeRate(request, type);
    }

    @Test
    void getExchangeRate_throwsException() {
        var request = new ExchangeRateRequest(SupportedCurrency.EUR, SupportedCurrency.AED);
        var type = Optional.of(DataSourceClientType.EXCHANGE_RATE_HOST);

        when(service.getExchangeRate(request, type)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.getExchangeRate(type, request));
    }

    @Test
    void getAllExchangeRates_happyPath() {
        var type = Optional.of(DataSourceClientType.EXCHANGE_RATE_HOST);

        controller.getAllExchangeRates(type, SupportedCurrency.EUR);

        verify(service, times(1)).getAllExchangeRates(SupportedCurrency.EUR, type);
    }

    @Test
    void getAllExchangeRates_throwException() {
        var type = Optional.of(DataSourceClientType.EXCHANGE_RATE_HOST);

        when(service.getAllExchangeRates(any(SupportedCurrency.class), eq(type)))
                .thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.getAllExchangeRates(type, SupportedCurrency.EUR));
    }
}