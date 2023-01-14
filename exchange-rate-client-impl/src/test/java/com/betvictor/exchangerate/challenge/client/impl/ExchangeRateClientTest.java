package com.betvictor.exchangerate.challenge.client.impl;

import com.betvictor.exchangerate.challenge.client.exception.ClientApiException;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.AED;
import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.EUR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateClient client;

    @Test
    void getExchangeRate_happyPath() {
        var dummy = new ExchangeRateApiResponse(true, "EUR", new Date(), new HashMap<>());
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(dummy);

        var result = client.getExchangeRate(EUR, AED);

        assertNotNull(result);
        verify(restTemplate).getForObject(anyString(), eq(ExchangeRateApiResponse.class));
    }

    @Test
    void getExchangeRate_templateThrowsException_shouldThrow_ClientApiException() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenThrow(new RuntimeException());

        assertThrows(ClientApiException.class, () -> client.getExchangeRate(EUR, AED));
    }

    @Test
    void getExchangeRate_nullResponse_shouldThrow_ClientApiException() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(null);

        assertThrows(ClientApiException.class, () -> client.getExchangeRate(EUR, AED));
    }

    @Test
    void getExchangeRate_responseWith_success_False_shouldThrow_ClientApiException() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(new ExchangeRateApiResponse(false, "EUR", new Date(), new HashMap<>()));

        assertThrows(ClientApiException.class, () -> client.getExchangeRate(EUR, AED));
    }

    @Test
    void getAllExchangeRates_happyPath() {
        var dummy = new ExchangeRateApiResponse(true, "EUR", new Date(), new HashMap<>());
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(dummy);

        var result = client.getAllExchangeRates(EUR);

        assertNotNull(result);
        verify(restTemplate).getForObject(anyString(), eq(ExchangeRateApiResponse.class));
    }

    @Test
    void getAllExchangeRates_templateThrowsException_shouldThrow_ClientApiException() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(null);

        assertThrows(ClientApiException.class, () -> client.getAllExchangeRates(EUR));
    }

    @Test
    void getAllExchangeRates_nullResponse_shouldThrow_ClientApiException() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(null);

        assertThrows(ClientApiException.class, () -> client.convert(Monetary.of(BigDecimal.TEN, EUR), EUR));
    }

    @Test
    void getAllExchangeRates_responseWith_success_False_shouldThrow_ClientApiException() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(new ExchangeRateApiResponse(false, "EUR", new Date(), new HashMap<>()));

        assertThrows(ClientApiException.class, () -> client.getAllExchangeRates(EUR));
    }

    @Test
    void convert_happyPath() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(new ExchangeRateApiResponse(true, "EUR", new Date(), Map.of("AED", 1.0)));

        var monetary = Monetary.of(BigDecimal.TEN, EUR);
        var result = client.convert(monetary, AED);

        var expected = List.of(Monetary.of(BigDecimal.valueOf(1.0), AED));

        assertEquals(monetary, result.monetary());
        assertEquals(expected, result.conversions());
    }

    @Test
    void convert_restTemplateThrowException_shouldThrow_ClientApiException() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenThrow(new RuntimeException());

        var monetary = Monetary.of(BigDecimal.TEN, EUR);

        assertThrows(ClientApiException.class, () -> client.convert(monetary, AED));
    }

    @Test
    void convert_nullResponse_shouldThrow_ClientApiException() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(null);

        var monetary = Monetary.of(BigDecimal.TEN, EUR);

        assertThrows(ClientApiException.class, () -> client.convert(monetary, AED));
    }

    @Test
    void convert_responseWithSuccess_False_shouldThrow_ClientApiException() {
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateApiResponse.class)))
                .thenReturn(new ExchangeRateApiResponse(false, "EUR", new Date(), new HashMap<>()));

        var monetary = Monetary.of(BigDecimal.TEN, EUR);

        assertThrows(ClientApiException.class, () -> client.convert(monetary, AED));
    }

}