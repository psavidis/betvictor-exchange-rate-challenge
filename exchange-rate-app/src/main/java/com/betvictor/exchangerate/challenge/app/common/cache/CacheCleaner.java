/*
 * ****************************************************************************
 * Copyright (c) Cortical.io GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with Cortical.io GmbH.
 * ***************************************************************************
 */

package com.betvictor.exchangerate.challenge.app.common.cache;

import com.betvictor.exchangerate.challenge.app.common.condition.DatasourceProvidersCachingEnabledCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * Component to clear the caches on demand.
 */
@Conditional(DatasourceProvidersCachingEnabledCondition.class)
@Component
public class CacheCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheCleaner.class);

    @Autowired
    private CacheManager cacheManager;

    /**
     * Clear all caches.
     */
    public void clean() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());

        LOGGER.info("All Caches have been cleared");
    }

}
