package com.sensiblemetrics.api.ws.document.generator.cache;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;

@Slf4j
public class DelegatedEhCacheEventListenerAdapter extends CacheEventListenerAdapter {

    @Override
    public void notifyElementRemoved(final Ehcache cache,
                                     final Element element) throws CacheException {
        log.debug(
                "Element removed: cache={}, key={}, creationTime={}, expirationTime={}",
                cache.getName(),
                element.getObjectKey(),
                element.getCreationTime(),
                element.getExpirationTime()
        );
    }

    @Override
    public void notifyElementPut(final Ehcache cache,
                                 final Element element) throws CacheException {
        log.debug(
                "Element put: cache={}, key={}, creationTime={}, expirationTime={}",
                cache.getName(),
                element.getObjectKey(),
                element.getCreationTime(),
                element.getExpirationTime()
        );
    }

    @Override
    public void notifyElementUpdated(final Ehcache cache,
                                     final Element element) throws CacheException {
        log.debug(
                "Element updated: cache={}, key={}, creationTime={}, expirationTime={}",
                cache.getName(),
                element.getObjectKey(),
                element.getCreationTime(),
                element.getExpirationTime()
        );
    }

    @Override
    public void notifyElementExpired(final Ehcache cache,
                                     final Element element) {
        log.debug(
                "Element expired: cache={}, key={}, creationTime={}, expirationTime={}",
                cache.getName(),
                element.getObjectKey(),
                element.getCreationTime(),
                element.getExpirationTime()
        );
    }

    @Override
    public void notifyElementEvicted(final Ehcache cache,
                                     final Element element) {
        log.debug(
                "Element evicted: cache={}, key={}, creationTime={}, expirationTime={}",
                cache.getName(),
                element.getObjectKey(),
                element.getCreationTime(),
                element.getExpirationTime()
        );
    }

    @Override
    public void notifyRemoveAll(final Ehcache cache) {
        log.debug(
                "RemoveAll elements from cache={}",
                cache.getName()
        );
    }

    @Override
    public void dispose() {
        log.debug("Cache disposed");
    }
}
