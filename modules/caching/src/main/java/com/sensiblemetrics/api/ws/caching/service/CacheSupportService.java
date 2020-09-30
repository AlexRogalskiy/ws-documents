package com.sensiblemetrics.api.ws.caching.service;

import com.sensiblemetrics.api.ws.commons.utils.ServiceUtils;
import org.springframework.cache.Cache;

import java.util.Optional;

/**
 * Cache support service implementation
 */
public class CacheSupportService {
    /**
     * Returns {@link V} value from {@link Cache} by input {@link K} key
     *
     * @param <K>   type of configurable key
     * @param <V>   type of configurable value
     * @param cache - initial input {@link Cache}
     * @param key   - initial input {@link K} key
     * @return {@link V} value
     */
    public <K, V> V getFromCache(final Cache cache,
                                 final K key) {
        final Cache.ValueWrapper valueWrapper = cache.get(key);
        return Optional.ofNullable(valueWrapper)
                .map(Cache.ValueWrapper::get)
                .<V>map(ServiceUtils::cast)
                .orElse(null);
    }

    /**
     * Updates {@link Cache} by input {@link K} key and {@link V} value
     *
     * @param <K>   type of configurable key
     * @param <V>   type of configurable value
     * @param cache - initial input {@link Cache}
     * @param key   - initial input {@link K} key
     * @param value - initial input {@link V} value
     * @return true - if {@link Cache} was updated, false - otherwise
     */
    public <K, V> boolean putCache(final Cache cache,
                                   final K key,
                                   final V value) {
        return Optional.ofNullable(value)
                .map(v -> {
                    cache.put(key, v);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Evicts item from {@link Cache} by input {@link K} key
     *
     * @param <K>   type of configurable key
     * @param cache - initial input {@link Cache}
     * @param key   - initial input {@link K} key
     * @return {@link Cache}
     */
    public <K> boolean evictFromCache(final Cache cache,
                                      final K key) {
        return Optional.ofNullable(key)
                .map(k -> {
                    cache.evict(k);
                    return true;
                })
                .orElse(false);
    }
}
