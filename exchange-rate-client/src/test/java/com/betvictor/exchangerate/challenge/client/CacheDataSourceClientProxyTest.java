package com.betvictor.exchangerate.challenge.client;

import com.betvictor.exchangerate.challenge.client.response.ExchangeRateApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collections;
import java.util.Date;

import static com.betvictor.exchangerate.challenge.domain.SupportedCurrency.EUR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CacheDataSourceClientProxyTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    private DataSourceClient client;

    private CacheDataSourceClientProxy proxy;

    @Test
    void proxy_shouldReturn_typeOf_DelegateClient() {
        when(cacheManager.getCache(anyString())).thenReturn(cache);

        this.client = new MockDataSourceClient();
        this.proxy = CacheDataSourceClientProxy.of(
                client,
                cacheManager
        );

        assertEquals(client.getType(), proxy.getType());
    }

    @Test
    void getAllExchangeRates_whenCache_doesNotContainEntry_getAllExchangeRates_shouldBeCalled() {
        when(cache.get(anyString())).thenReturn(null);
        when(cacheManager.getCache(anyString())).thenReturn(cache);

        this.client = mock(DataSourceClient.class);
        when(client.getType()).thenReturn(DataSourceClientType.EXCHANGE_RATE_HOST);

        this.proxy = CacheDataSourceClientProxy.of(
                client,
                cacheManager
        );

        proxy.getAllExchangeRates(EUR);

        verify(client).getAllExchangeRates(EUR);
    }

    @Test
    void getAllExchangeRates_whenCache_ContainsEntry_getAllExchangeRates_shouldNotBeCalled() {
        when(cache.get(anyString())).thenReturn(() -> new ExchangeRateApiResponse(
                true, "", new Date(), Collections.emptyMap()
        ));

        when(cacheManager.getCache(anyString())).thenReturn(cache);

        this.client = mock(DataSourceClient.class);
        when(client.getType()).thenReturn(DataSourceClientType.EXCHANGE_RATE_HOST);

        this.proxy = CacheDataSourceClientProxy.of(
                client,
                cacheManager
        );

        proxy.getAllExchangeRates(EUR);

        verify(client, times(0)).getAllExchangeRates(any());
    }
}
