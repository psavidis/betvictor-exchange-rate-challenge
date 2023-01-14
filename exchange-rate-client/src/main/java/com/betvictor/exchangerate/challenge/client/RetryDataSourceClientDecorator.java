package com.betvictor.exchangerate.challenge.client;

import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import com.betvictor.exchangerate.challenge.domain.Monetary;
import com.betvictor.exchangerate.challenge.domain.SupportedCurrency;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;

/**
 * Decorator to extend a {@link DataSourceClient} with Retries using a {@link RetryTemplate}.
 */
public class RetryDataSourceClientDecorator implements DataSourceClient {

    private final DataSourceClient delegate;
    private final RetryTemplate retryTemplate;

    private RetryDataSourceClientDecorator(DataSourceClient delegate, RetryTemplate retryTemplate) {
        this.delegate = delegate;
        this.retryTemplate = retryTemplate;
    }

    public static RetryDataSourceClientDecorator of(DataSourceClient delegate, RetryTemplate retryTemplate) {
        return new RetryDataSourceClientDecorator(delegate, retryTemplate);
    }

    @Override
    public ExchangeRateApiResponse getExchangeRate(SupportedCurrency fromCurrency, SupportedCurrency toCurrency) {
        return retryTemplate.execute((c) -> delegate.getExchangeRate(fromCurrency, toCurrency));
    }

    @Override
    public ExchangeRateApiResponse getAllExchangeRates(SupportedCurrency currency) {
        return retryTemplate.execute((c) -> delegate.getAllExchangeRates(currency));
    }

    @Override
    public ConversionResponse convert(Monetary monetary, SupportedCurrency toCurrency) {
        return retryTemplate.execute((c) -> delegate.convert(monetary, toCurrency));
    }

    @Override
    public ConversionResponse convert(Monetary monetary, List<SupportedCurrency> toCurrencies) {
        return retryTemplate.execute((c) -> delegate.convert(monetary, toCurrencies));
    }

    @Override
    public DataSourceClientType getType() {
        return delegate.getType();
    }
}
