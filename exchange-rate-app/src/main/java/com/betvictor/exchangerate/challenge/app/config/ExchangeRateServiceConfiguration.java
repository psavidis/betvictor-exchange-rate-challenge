package com.betvictor.exchangerate.challenge.app.config;

import com.betvictor.exchangerate.challenge.app.model.service.ExchangeService;
import com.betvictor.exchangerate.challenge.client.DataSourceClientFactory;
import com.betvictor.exchangerate.challenge.client.DataSourceClientType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeRateServiceConfiguration {

    @Bean
    public ExchangeService dataSourceClient(
            DataSourceClientFactory clientFactory,
            @Value("${application.default-datasource-provider:EXCHANGE_RATE_HOST}") DataSourceClientType defaultClientType
    ) {
        return new ExchangeService(clientFactory, defaultClientType);
    }
}