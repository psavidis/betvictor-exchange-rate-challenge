package com.betvictor.exchangerate.challenge.app.common.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition that fires true when caching is enabled for datasource providers.
 */
public class DatasourceProvidersCachingEnabledCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var cachingEnabled = context.getEnvironment().getProperty("application.datasource-provider.enable-caching");

        return "true".equals(cachingEnabled);
    }
}
