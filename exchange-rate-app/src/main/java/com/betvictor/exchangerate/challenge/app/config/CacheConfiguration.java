/*
 * ****************************************************************************
 * Copyright (c) Cortical.io GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with Cortical.io GmbH.
 * ***************************************************************************
 */

package com.betvictor.exchangerate.challenge.app.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Cache configuration for COIN spring context.
 */
@ConditionalOnProperty(value = "application.datasource-provider.enable-caching", havingValue = "true")
@EnableCaching
@Configuration
public class CacheConfiguration {
}