package com.sensiblemetrics.api.ws.webdocs.document.generator.cache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class DelegatedCacheEventListener implements CacheEventListener<String, CacheEventEntry> {

  @Override
  public void onEvent(final CacheEvent<? extends String, ? extends CacheEventEntry> cacheEvent) {
    log.debug(
        String.format(
            "DelegatedCacheEventListener: event: {%s}, key: {%s}, old value: {%s}, new value: {%s}",
            cacheEvent.getType(),
            cacheEvent.getKey(),
            cacheEvent.getOldValue(),
            cacheEvent.getNewValue()));
  }
}
