package com.sensiblemetrics.api.ws.document.generator.cache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
public class DelegatedCacheLoaderWriter implements CacheLoaderWriter<String, Boolean> {

    /**
     * Default cache initial size
     */
    public static final int DEFAULT_CACHE_INITIAL_SIZE = 1_000;

    /**
     * Default cache instance
     */
    private final Map<String, Boolean> cacheMap;

    public DelegatedCacheLoaderWriter() {
        this.cacheMap = new ConcurrentHashMap<>(DEFAULT_CACHE_INITIAL_SIZE);
    }

    @Override
    public Boolean load(final String key) {
        log.debug(String.format("DelegatedCacheLoaderWriter: loading key: {%s}", key));
        return this.getCacheMap().putIfAbsent(key, Boolean.TRUE);
    }

    @Override
    public Map<String, Boolean> loadAll(final Iterable<? extends String> iterable) {
        log.debug(String.format(
                "DelegatedCacheLoaderWriter: loading all keys: {%s}",
                StringUtils.join(iterable, "|"))
        );
        this.getCacheMap().clear();
        Optional.ofNullable(iterable)
                .orElseGet(Collections::emptyList)
                .forEach(this::load);
        return this.getCacheMap();
    }

    @Override
    public void write(final String key, final Boolean value) {
        log.debug(String.format("DelegatedCacheLoaderWriter: writing entry with key: {%s}, value: {%s}", key, value));
        this.getCacheMap().put(key, value);
    }

    @Override
    public void writeAll(final Iterable<? extends Map.Entry<? extends String, ? extends Boolean>> iterable) {
        log.debug(String.format(
                "DelegatedCacheLoaderWriter: writing all entries: {%s}",
                StringUtils.join(iterable, "|"))
        );
        Optional.ofNullable(iterable)
                .orElseGet(Collections::emptyList)
                .forEach(entry -> this.write(entry.getKey(), entry.getValue()));
    }

    @Override
    public void delete(final String key) {
        log.debug(String.format("DelegatedCacheLoaderWriter: deleting key: {%s}", key));
        this.getCacheMap().remove(key);
    }

    @Override
    public void deleteAll(final Iterable<? extends String> iterable) {
        log.debug(String.format(
                "DelegatedCacheLoaderWriter: deleting all keys: {%s}",
                StringUtils.join(iterable, "|"))
        );
        Optional.ofNullable(iterable)
                .orElseGet(Collections::emptyList)
                .forEach(this::delete);
    }
}
