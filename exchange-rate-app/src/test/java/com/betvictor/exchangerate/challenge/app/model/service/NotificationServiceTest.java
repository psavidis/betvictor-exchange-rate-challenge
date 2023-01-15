package com.betvictor.exchangerate.challenge.app.model.service;

import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.AED;
import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.CAD;
import static java.math.BigDecimal.ZERO;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private NotificationService service;

    @BeforeEach
    void init() {
        this.service = new NotificationService(restTemplate);
    }

    @Test
    void exchangeRateListResponse() {
        service.notify(UUID.randomUUID(), "url", new ExchangeRateListResponse(
                CAD, List.of(Monetary.of(ZERO, AED))
        ));

        verify(restTemplate).postForEntity(anyString(), any(), eq(Void.class));;
    }

    @Test
    void exchangeRateResponse() {
        service.notify(UUID.randomUUID(), "url", new ExchangeRateResponse(
                CAD, Monetary.of(ZERO, AED)
        ));

        verify(restTemplate).postForEntity(anyString(), any(), eq(Void.class));;
    }

    @Test
    void valueConversionResponse() {
        service.notify(UUID.randomUUID(), "url", new ValueConversionResponse(
                Monetary.of(ZERO, AED), Monetary.of(ZERO, AED)
        ));

        verify(restTemplate).postForEntity(anyString(), any(), eq(Void.class));;
    }

    @Test
    void valueConversionListResponse() {
        service.notify(UUID.randomUUID(), "url", new ValueConversionListResponse(
                Monetary.of(ZERO, AED), List.of(Monetary.of(ZERO, AED))
        ));

        verify(restTemplate).postForEntity(anyString(), any(), eq(Void.class));;
    }

}
