package com.betvictor.exchangerate.challenge.messaging;

import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import com.betvictor.exchangerate.challenge.messaging.dto.ConversionRequest;
import com.betvictor.exchangerate.challenge.messaging.dto.ExchangeRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.betvictor.exchangerate.challenge.messaging.OperationType.CONVERSION;
import static com.betvictor.exchangerate.challenge.messaging.OperationType.EXCHANGE_RATE;

/**
 * Service to be used for sending exchange & convert async requests.
 */
@Service
public class MessagingService {

    private final MessagePublisher publisher;

    public MessagingService(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    public void sendExchangeRequest(Optional<DataSourceClientType> type,
                                    SupportedCurrency fromCurrency,
                                    SupportedCurrency toCurrency,
                                    String callBackURL
    ) {
        var contextId = UUID.randomUUID();
        var datasourceType = type.orElse(null);
        var metadata = new Metadata(datasourceType, new ExchangeRequest(fromCurrency, toCurrency));

        publisher.send(contextId, EXCHANGE_RATE, metadata, callBackURL);
    }

    public void sendConversionRequest(Optional<DataSourceClientType> type,
                                      BigDecimal value,
                                      SupportedCurrency currency,
                                      List<SupportedCurrency> toCurrencies,
                                      String callBackURL
    ) {
        var contextId = UUID.randomUUID();
        var datasourceType = type.orElse(null);
        var metadata = new Metadata(datasourceType, new ConversionRequest(
                value, currency, toCurrencies
        ));

        publisher.send(contextId, CONVERSION, metadata, callBackURL);
    }
}