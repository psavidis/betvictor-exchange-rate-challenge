package com.betvictor.exchangerate.challenge.app.messaging.endpoint;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.model.service.NotificationService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ConversionRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeOperationRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.betvictor.exchangerate.challenge.client.DataSourceClientType.EXCHANGE_RATE_HOST;
import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.EUR;
import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.USD;
import static com.betvictor.exchangerate.challenge.messaging.dto.request.OperationType.EXCHANGE_RATE;
import static java.math.BigDecimal.ZERO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeOperationRequestHandlerTest {

    @Mock
    private ExchangeService exchangeService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ExchangeOperationRequestHandler handler;

    @BeforeEach
    void init() {
        this.handler = new ExchangeOperationRequestHandler(exchangeService, notificationService);
    }

    @Test
    void exchangeOperationRequest_all() {
        when(exchangeService.getAllExchangeRates(any(), any())).thenReturn(new ExchangeRateListResponse(
                EUR, List.of(Monetary.of(ZERO, USD))
        ));

        var request = new ExchangeOperationRequest(
                UUID.randomUUID(),
                EXCHANGE_RATE,
                new Metadata(EXCHANGE_RATE_HOST, new ExchangeRequest(EUR, null)),
                "url",
                Instant.now()
        );

        handler.handleExchange(request);

        verify(exchangeService).getAllExchangeRates(any(SupportedCurrency.class), any(Optional.class));
        verify(notificationService).notify(eq(request.contextId()), eq(request.callBackURL()), any(ExchangeRateListResponse.class));
    }

    @Test
    void exchangeOperationRequest_single() {
        when(exchangeService.getExchangeRate(any(), any())).thenReturn(new ExchangeRateResponse(
                EUR, Monetary.of(ZERO, USD)
        ));

        var request = new ExchangeOperationRequest(
                UUID.randomUUID(),
                EXCHANGE_RATE,
                new Metadata(EXCHANGE_RATE_HOST, new ExchangeRequest(EUR, USD)),
                "url",
                Instant.now()
        );

        handler.handleExchange(request);

        verify(exchangeService).getExchangeRate(any(ExchangeRateRequest.class), any(Optional.class));
        verify(notificationService).notify(eq(request.contextId()), eq(request.callBackURL()), any(ExchangeRateResponse.class));
    }

    @Test
    void conversionOperationRequest_all() {
        var response = new ValueConversionListResponse(
                Monetary.of(ZERO, USD),
                List.of(Monetary.of(ZERO, USD), Monetary.of(ZERO, USD))
        );

        when(exchangeService.convert(any(ConvertValueListRequest.class), any(Optional.class)))
                .thenReturn(response);

        var request = new ExchangeOperationRequest(
                UUID.randomUUID(),
                EXCHANGE_RATE,
                new Metadata(EXCHANGE_RATE_HOST, new ConversionRequest(ZERO, EUR, List.of(EUR, USD))),
                "url",
                Instant.now()
        );

        handler.handleConversion(request);

        verify(exchangeService).convert(any(ConvertValueListRequest.class), any(Optional.class));
        verify(notificationService).notify(eq(request.contextId()), eq(request.callBackURL()), any(ValueConversionListResponse.class));
    }

    @Test
    void conversionOperationRequest_single() {
        var response = new ValueConversionResponse(
                Monetary.of(ZERO, USD),
                Monetary.of(ZERO, USD)
        );

        when(exchangeService.convert(any(ConvertValueRequest.class), any(Optional.class)))
                .thenReturn(response);

        var request = new ExchangeOperationRequest(
                UUID.randomUUID(),
                EXCHANGE_RATE,
                new Metadata(EXCHANGE_RATE_HOST, new ConversionRequest(ZERO, EUR, List.of(EUR))),
                "url",
                Instant.now()
        );

        handler.handleConversion(request);

        verify(exchangeService).convert(any(ConvertValueRequest.class), any(Optional.class));
        verify(notificationService).notify(eq(request.contextId()), eq(request.callBackURL()), any(ValueConversionResponse.class));
    }
}
