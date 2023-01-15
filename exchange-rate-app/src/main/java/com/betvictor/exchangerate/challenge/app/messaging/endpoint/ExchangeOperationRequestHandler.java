package com.betvictor.exchangerate.challenge.app.messaging.endpoint;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.app.model.service.NotificationService;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ExchangeRateRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ConversionRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeOperationRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.request.ExchangeRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExchangeOperationRequestHandler {

    private final ExchangeService exchangeService;
    private final NotificationService notificationService;

    public ExchangeOperationRequestHandler(ExchangeService exchangeService,
                                           NotificationService notificationService) {
        this.exchangeService = exchangeService;
        this.notificationService = notificationService;
    }

    public void handleConversion(ExchangeOperationRequest event) {
        var metadata = event.metadata();
        var request = (ConversionRequest) metadata.request();
        var providerType = Optional.ofNullable(metadata.datasourceProvider());

        if (request.isList()) {
            var response = exchangeService.convert(new ConvertValueListRequest(
                    request.value(),
                    request.currency(),
                    request.toCurrencies()), providerType);

            notificationService.notify(event.contextId(), event.callBackURL(), response);
            return;
        }

        var response = exchangeService.convert(new ConvertValueRequest(
                request.value(),
                request.currency(),
                request.toCurrencies().get(0)), providerType);

        notificationService.notify(event.contextId(), event.callBackURL(), response);
    }

    public void handleExchange(ExchangeOperationRequest event) {
        var metadata = event.metadata();
        var request = (ExchangeRequest) metadata.request();
        var providerType = Optional.ofNullable(metadata.datasourceProvider());

        if (request.isAll()) {
            var response = exchangeService.getAllExchangeRates(
                    request.getFromCurrency(),
                    providerType
            );

            notificationService.notify(event.contextId(), event.callBackURL(), response);
            return;
        }

        var response = exchangeService.getExchangeRate(new ExchangeRateRequest(
                        request.getFromCurrency(),
                        request.getToCurrency()),
                providerType
        );

        notificationService.notify(event.contextId(), event.callBackURL(), response);
    }
}
