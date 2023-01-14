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

import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.MapEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryEvictedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.hazelcast.map.listener.MapClearedListener;
import com.hazelcast.map.listener.MapEvictedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used by Hazelcast configuration to log the cache events for debugging purposes. Usage in production can reduce the
 * performance due to calling the logger for each entry change.
 */
public class CacheLogger implements EntryAddedListener<String, String>,
    EntryRemovedListener<String, String>, EntryUpdatedListener<String, String>,
    EntryEvictedListener<String, String>, MapEvictedListener, MapClearedListener {

  private static final Logger LOG = LoggerFactory.getLogger(CacheLogger.class);

  @Override
  public void entryAdded(EntryEvent<String, String> event) {
    LOG.debug("Entry added: {}", event);
  }

  @Override
  public void entryEvicted(EntryEvent<String, String> event) {
    LOG.debug("Entry evicted: {}", event);
  }

  @Override
  public void entryRemoved(EntryEvent<String, String> event) {
    LOG.debug("Entry removed: {}", event);
  }

  @Override
  public void entryUpdated(EntryEvent<String, String> event) {
    LOG.debug("Entry updated: {}", event);
  }

  @Override
  public void mapCleared(MapEvent event) {
    LOG.debug("Entry cleared: {}", event);
  }

  @Override
  public void mapEvicted(MapEvent event) {
    LOG.debug("Entry evicted: {}", event);
  }
}
