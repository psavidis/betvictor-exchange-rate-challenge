package com.betvictor.exchangerate.challenge.app.config;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.client.DataSourceClient;
import com.betvictor.exchangerate.challenge.client.DataSourceClientFactory;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

import java.util.List;

@Configuration
public class ExchangeRateServiceConfiguration {

    @Bean
    public DataSourceClientFactory dataSourceClientFactory(
            @Value("${application.datasource-provider.default:EXCHANGE_RATE_HOST}") DataSourceClientType defaultClientType,
            @Value("${application.datasource-provider.enable-caching:false}") boolean enableCaching,
            @Autowired(required = false) CacheManager cacheManager,
            List<DataSourceClient> clients
    ) {
        return DataSourceClientFactory.of(clients, defaultClientType, cacheManager, enableCaching);
    }

    @Bean
    public ExchangeService dataSourceClient(
            DataSourceClientFactory clientFactory,
            ConversionService conversionService
    ) {
        return new ExchangeService(clientFactory, conversionService);
    }
}