package com.betvictor.exchangerate.challenge.client;

import com.betvictor.exchangerate.challenge.domain.Monetary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;

import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.AED;
import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.EUR;
import static java.math.BigDecimal.ZERO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RetryDataSourceClientDecoratorTest {

    @Mock
    private DataSourceClient delegate;

    @Mock
    private RetryTemplate retryTemplate;

    private RetryDataSourceClientDecorator decorator;

    @BeforeEach
    void init() {
        decorator = RetryDataSourceClientDecorator.of(delegate, retryTemplate);
    }

    @Test
    void getExchangeRate() {
        decorator.getExchangeRate(EUR, AED);

        verify(retryTemplate).execute(any(RetryCallback.class), isNull(), isNull());
    }

    @Test
    void getAllExchangeRates() {
        decorator.getAllExchangeRates(EUR);

        verify(retryTemplate).execute(any(RetryCallback.class), isNull(), isNull());
    }

    @Test
    void convert_single() {
        decorator.convert(Monetary.of(ZERO, EUR), EUR);

        verify(retryTemplate).execute(any(RetryCallback.class), isNull(), isNull());
    }

    @Test
    void convert_list() {
        decorator.convert(Monetary.of(ZERO, EUR), List.of(EUR));

        verify(retryTemplate).execute(any(RetryCallback.class), isNull(), isNull());
    }
}