package com.betvictor.exchangerate.challenge.app.web.controller;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.betvictor.exchangerate.challenge.client.DataSourceClientType.EXCHANGE_RATE_HOST;
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
        var type = Optional.of(EXCHANGE_RATE_HOST);

        controller.convertToCurrency(type, request);

        verify(service, times(1)).convert(request, type);
    }

    @Test
    void convertToCurrency_throwException() {
        when(service.convert(any(ConvertValueRequest.class), any(Optional.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.convertToCurrency(Optional.of(EXCHANGE_RATE_HOST), new ConvertValueRequest(null, null, null)));
    }

    @Test
    void convertToCurrencies_happyPath() {
        var type = Optional.of(EXCHANGE_RATE_HOST);
        var request = new ConvertValueListRequest(null, null, null);

        controller.convertToCurrencies(type, request);

        verify(service, times(1)).convert(request, type);
    }

    @Test
    void convert_throwException() {
        when(service.convert(any(ConvertValueListRequest.class), any(Optional.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.convertToCurrencies(Optional.of(EXCHANGE_RATE_HOST), new ConvertValueListRequest(null, null, null)));
    }

}