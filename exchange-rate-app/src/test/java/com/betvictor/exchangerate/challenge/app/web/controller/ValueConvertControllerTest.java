package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValueConvertControllerTest {

    @Mock
    private ExchangeService service;

    @InjectMocks
    private ValueConvertController controller;

    @Test
    void convertToCurrency_happyPath() {
        var request = new ConvertValueRequest(null, null, null);
        controller.convertToCurrency(request);

        verify(service, times(1)).convert(request);
    }

    @Test
    void convertToCurrency_throwException() {
        when(controller.convertToCurrency(any(ConvertValueRequest.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.convertToCurrency(new ConvertValueRequest(null, null, null)));
    }

    @Test
    void convertToCurrencies_happyPath() {
        var request = new ConvertValueListRequest(null, null, null);
        controller.convertToCurrencies(request);

        verify(service, times(1)).convert(request);
    }

    @Test
    void convertToCurrencies_throwException() {
        when(controller.convertToCurrencies(any(ConvertValueListRequest.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.convertToCurrencies(new ConvertValueListRequest(null, null, null)));
    }

}