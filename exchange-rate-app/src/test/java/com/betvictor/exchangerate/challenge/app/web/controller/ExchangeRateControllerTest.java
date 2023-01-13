package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        controller.getExchangeRate(request);

        verify(service, times(1)).getExchangeRate(request);
    }

    @Test
    void getExchangeRate_throwsException() {
        var request = new ExchangeRateRequest(SupportedCurrency.EUR, SupportedCurrency.AED);
        when(service.getExchangeRate(request)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.getExchangeRate(request));
    }

    @Test
    void getAllExchangeRates_happyPath() {
        controller.getAllExchangeRates(SupportedCurrency.EUR);

        verify(service, times(1)).getAllExchangeRates(SupportedCurrency.EUR);
    }

    @Test
    void getAllExchangeRates_throwException() {
        when(service.getAllExchangeRates(any(SupportedCurrency.class)))
                .thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.getAllExchangeRates(SupportedCurrency.EUR));
    }
}