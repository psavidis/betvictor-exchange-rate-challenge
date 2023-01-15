package com.betvictor.exchangerate.challenge.messaging.service;

import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import com.betvictor.exchangerate.challenge.messaging.MessagePublisher;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ConversionRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.betvictor.exchangerate.challenge.client.DataSourceClientType.EXCHANGE_RATE_HOST;
import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.USD;
import static com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType.CONVERSION;
import static com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType.EXCHANGE_RATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessagingServiceTest {

    @Captor
    private ArgumentCaptor<Metadata> metadataCaptor;

    @Mock
    private MessagePublisher publisher;

    private MessagingService service;

    @BeforeEach
    void init() {
        service = new MessagingService(publisher);
    }

    @Test
    void exchangeRate_happyPath() {
        service.sendExchangeRequest(
                Optional.of(EXCHANGE_RATE_HOST),
                SupportedCurrency.EUR,
                USD,
                "url"
        );

        verify(publisher).send(
                any(UUID.class),
                eq(EXCHANGE_RATE),
                metadataCaptor.capture(),
                eq("url")
        );

        var result = metadataCaptor.getValue();

        assertEquals(EXCHANGE_RATE_HOST, result.datasourceProvider());
        assertTrue(result.request() instanceof ExchangeRequest);
    }

    @Test
    void conversion_happyPath() {
        service.sendConversionRequest(
                Optional.of(EXCHANGE_RATE_HOST),
                BigDecimal.ZERO,
                SupportedCurrency.EUR,
                Collections.emptyList(),
                "url"
        );

        verify(publisher).send(
                any(UUID.class),
                eq(CONVERSION),
                metadataCaptor.capture(),
                eq("url")
        );

        var result = metadataCaptor.getValue();

        assertEquals(EXCHANGE_RATE_HOST, result.datasourceProvider());
        assertTrue(result.request() instanceof ConversionRequest);
    }
}
