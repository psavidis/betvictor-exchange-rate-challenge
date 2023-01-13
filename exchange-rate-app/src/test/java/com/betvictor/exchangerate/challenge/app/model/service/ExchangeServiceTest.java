package com.betvictor.exchangerate.challenge.app.model.service;

import com.betvictor.exchangerate.challenge.app.model.ExchangeContext;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import com.betvictor.exchangerate.challenge.client.DataSourceClientFactory;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.betvictor.exchangerate.challenge.client.DataSourceClientType.EXCHANGE_RATE_HOST;
import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.EUR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
public class ExchangeServiceTest {

    @Mock
    private DataSourceClientFactory clientFactory;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private ExchangeService service;

    @Test
    void getExchangeRate_happyPath() {
        var request = new ExchangeRateRequest(null, null);
        var type = Optional.of(EXCHANGE_RATE_HOST);

        when(clientFactory.getClient(any(Optional.class))).thenReturn(new MockDataSourceClient());
        when(conversionService.convert(any(ExchangeContext.class), eq(ExchangeRateResponse.class)))
                .thenReturn(new ExchangeRateResponse(null, null));

        var result = service.getExchangeRate(request, type);

        assertNotNull(result);
        verify(clientFactory).getClient(type);
        verify(conversionService).convert(any(), any());
    }

    @Test
    void getAllExchangeRates_happyPath() {
        when(clientFactory.getClient(any(Optional.class))).thenReturn(new MockDataSourceClient());
        when(conversionService.convert(any(ExchangeRateApiResponse.class), eq(ExchangeRateListResponse.class)))
                .thenReturn(new ExchangeRateListResponse(EUR, new ArrayList<>()));

        var type = Optional.of(EXCHANGE_RATE_HOST);
        var result = service.getAllExchangeRates(EUR, type);

        assertNotNull(result);
        verify(clientFactory).getClient(eq(type));
        verify(conversionService).convert(any(), eq(ExchangeRateListResponse.class));
    }

    @Test
    void convert_happyPath() {
        when(clientFactory.getClient(any())).thenReturn(new MockDataSourceClient());
        when(conversionService.convert(any(), eq(ValueConversionResponse.class)))
                .thenReturn(new ValueConversionResponse(null, null));

        var result = service.convert(new ConvertValueRequest(BigDecimal.valueOf(1.0), EUR, EUR), Optional.empty());

        assertNotNull(result);
        verify(clientFactory).getClient(any());
        verify(conversionService).convert(any(), eq(ValueConversionResponse.class));
    }

    @Test
    void convertList_happyPath() {
        when(clientFactory.getClient(any())).thenReturn(new MockDataSourceClient());
        when(conversionService.convert(any(), eq(ValueConversionListResponse.class)))
                .thenReturn(new ValueConversionListResponse(null, null));

        var result = service.convert(new ConvertValueListRequest(BigDecimal.valueOf(1.0), EUR, List.of(EUR)), Optional.empty());

        assertNotNull(result);
        verify(clientFactory).getClient(any());
        verify(conversionService).convert(any(), eq(ValueConversionListResponse.class));
    }

    @Test
    void getExchangeRate_getClient_throwsException() {
        var request = new ExchangeRateRequest(null, null);
        var type = Optional.of(EXCHANGE_RATE_HOST);

        when(clientFactory.getClient(any(Optional.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.getExchangeRate(request, type));
    }

    @Test
    void getAllExchangeRates_getClient_throwsException() {
        var type = Optional.of(EXCHANGE_RATE_HOST);

        when(clientFactory.getClient(any(Optional.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.getAllExchangeRates(EUR, type));
    }

    @Test
    void convert_list_getClient_throwsException() {
        var type = Optional.of(EXCHANGE_RATE_HOST);
        when(clientFactory.getClient(any(Optional.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.convert(new ConvertValueListRequest(null, null, null), type));
    }

    @Test
    void convert_getClient_throwsException() {
        var type = Optional.of(EXCHANGE_RATE_HOST);
        when(clientFactory.getClient(any(Optional.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.convert(new ConvertValueRequest(null, null, null), type));
    }
}
