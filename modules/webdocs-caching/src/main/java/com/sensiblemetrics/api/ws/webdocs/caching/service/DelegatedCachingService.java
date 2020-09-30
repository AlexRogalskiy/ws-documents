package com.sensiblemetrics.api.ws.webdocs.caching.service;

import com.sensiblemetrics.api.ws.webdocs.commons.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/** Delegated caching service implementation */
@Slf4j
@Transactional
public class DelegatedCachingService implements CachingService {
  private final CacheManager cacheManager;

  /**
   * Default delegated caching service constructor by input {@link CacheManager} instance
   *
   * @param cacheManager - initial input {@link CacheManager} instance
   */
  public DelegatedCachingService(final CacheManager cacheManager) {
    Assert.notNull(cacheManager, "Cache manager should not be null");
    this.cacheManager = cacheManager;
  }

  /**
   * {@inheritDoc}
   *
   * @see CachingService
   */
  @Override
  public <K, V> void put(final String cacheName, final K key, final V value) {
    log.debug("Storing cache entry in cache: {}, key: {}, value: {}", cacheName, key, value);
    Optional.ofNullable(cacheName)
        .map(this.cacheManager::getCache)
        .ifPresent(cache -> cache.put(key, value));
  }

  /**
   * {@inheritDoc}
   *
   * @see CachingService
   */
  @Override
  public <K, V> V get(final String cacheName, final K key) {
    log.debug("Fetching cache entry in cache: {}, key: {}", cacheName, key);
    return Optional.ofNullable(cacheName)
        .map(this.cacheManager::getCache)
        .map(cache -> cache.get(key))
        .map(Cache.ValueWrapper::get)
        .<V>map(ServiceUtils::cast)
        .orElse(null);
  }

  /**
   * {@inheritDoc}
   *
   * @see CachingService
   */
  @Override
  public <K> void evict(final String cacheName, final K key) {
    log.debug("Removing cache entry in cache: {}, key: {}", cacheName, key);
    Optional.ofNullable(cacheName)
        .map(this.cacheManager::getCache)
        .ifPresent(cache -> cache.evict(key));
  }

  /**
   * {@inheritDoc}
   *
   * @see CachingService
   */
  @Override
  public void evictAll(final String cacheName) {
    log.debug("Removing all cache entries in cache: {}", cacheName);
    Optional.ofNullable(cacheName).map(this.cacheManager::getCache).ifPresent(Cache::clear);
  }

  /**
   * {@inheritDoc}
   *
   * @see CachingService
   */
  public void evictAllCaches() {
    log.debug("Removing all cache entries in all caches: {}", this.cacheManager.getCacheNames());
    this.cacheManager.getCacheNames().parallelStream().forEach(this::evictAll);
  }
}
