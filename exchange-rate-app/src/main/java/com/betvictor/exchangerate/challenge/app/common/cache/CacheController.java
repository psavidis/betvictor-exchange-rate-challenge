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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Conditional(DatasourceProvidersCachingEnabledCondition.class)
@RestController
@CrossOrigin
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheCleaner cacheCleaner;

    @GetMapping(value = "/clear")
    public String clearCache() {
        cacheCleaner.clean();
        return "OK";
    }

}
