package com.betvictor.exchangerate.challenge.client;

import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.betvictor.exchangerate.challenge.client.DataSourceClientType.EXCHANGE_RATE_HOST;
import static com.betvictor.exchangerate.challenge.client.DataSourceClientType.FREE_CURRENCY_API;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class DataSourceClientFactoryTest {

    private DataSourceClientFactory factory;

    private DataSourceClient mockClient = mockClient(EXCHANGE_RATE_HOST);
    private DataSourceClient mockClient2 = mockClient(FREE_CURRENCY_API);

    private DataSourceClientType defaultType = EXCHANGE_RATE_HOST;

    @BeforeEach
    void init() {
        factory = DataSourceClientFactory.of(List.of(mockClient, mockClient2), defaultType);
    }

    @Test
    void getClient_emptyOptional_shouldReturn_defaultClientType() {
        var result = factory.getClient(Optional.empty());

        assertNotNull(result);
        assertEquals(EXCHANGE_RATE_HOST, result.getType());
    }

    @Test
    void getClient_happyPath() {
        var result = factory.getClient(Optional.of(FREE_CURRENCY_API));

        assertNotNull(result);
        assertEquals(FREE_CURRENCY_API, result.getType());
    }

    private DataSourceClient mockClient(DataSourceClientType type) {
        return new DataSourceClient() {
            @Override
            public ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
                return null;
            }

            @Override
            public ExchangeRateApiResponse getAllExchangeRates(SupportedCurrency currency) {
                return null;
            }

            @Override
            public ConversionResponse convert(Monetary monetary, SupportedCurrency toCurrency) {
                return null;
            }

            @Override
            public ConversionResponse convert(Monetary monetary, List<SupportedCurrency> toCurrencies) {
                return null;
            }

            @Override
            public DataSourceClientType getType() {
                return type;
            }
        };
    }
}
