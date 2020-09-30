package com.sensiblemetrics.api.ws.webdocs.caching.service;

/** Caching service declaration */
public interface CachingService {
  /**
   * Stores input {@code K}/{@code V} cache entry by {@link String} cache name
   *
   * @param <K> type of configurable key
   * @param <V> type of configurable value
   * @param cacheName - initial input {@link String} cache name to store by
   * @param key - initial input {@link K} entry key
   * @param value - initial input {@link V} entry value
   */
  <K, V> void put(final String cacheName, final K key, final V value);

  /**
   * Returns {@code V} cache entry value by input {@link String} cache name and {@link K}
   *
   * @param <K> type of configurable key
   * @param <V> type of configurable value
   * @param cacheName - initial input {@link String} cache name to fetch by
   * @param key - initial input {@link K} entry key
   * @return {@code V} cache entry value
   */
  <K, V> V get(final String cacheName, final K key);

  /**
   * Removes cache entry by input {@link String} cache name and {@link K}
   *
   * @param <K> type of configurable key
   * @param cacheName - initial input {@link String} cache name to fetch by
   * @param key - initial input {@link K} entry key
   */
  <K> void evict(final String cacheName, final K key);

  /**
   * Removes all cache entries by input {@link String} cache name
   *
   * @param cacheName - initial input {@link String} cache name to fetch by
   */
  void evictAll(final String cacheName);

  /** Removes all caches */
  void evictAllCaches();
}
