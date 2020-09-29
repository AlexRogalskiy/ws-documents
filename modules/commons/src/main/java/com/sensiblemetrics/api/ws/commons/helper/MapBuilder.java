package com.sensiblemetrics.api.ws.commons.helper;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Builder for maps, which also conveniently implements {@link Map} via delegation for convenience
 * so you don't have to actually {@link #build()} it.
 *
 * @param <K> The key type of the map.
 * @param <V> The value type of the map.
 */
public class MapBuilder<K, V> implements Map<K, V> {
  private final Map<K, V> map;

  /** Create a new {@link MapBuilder}. */
  public MapBuilder() {
    this(Collections.emptyMap());
  }

  /**
   * Create a new instance with a copy of the given map.
   *
   * @param source must not be {@literal null}
   */
  public MapBuilder(final Map<K, V> source) {
    Assert.notNull(source, "Source map must not be null");
    this.map = new LinkedHashMap<>(source);
  }

  /**
   * Factory method to construct a new {@code MapBuilder<Object,Object>}. Convenient if imported
   * statically.
   */
  @NonNull
  public static MapBuilder<Object, Object> map() {
    return map(Object.class, Object.class);
  }

  /**
   * Factory method to construct a new builder with the given key &amp; value types. Convenient if
   * imported statically.
   */
  @NonNull
  public static <K, V> MapBuilder<K, V> map(final Class<K> keyType, final Class<V> valueType) {
    return new MapBuilder<>();
  }

  /**
   * Factory method to construct a new builder with a shallow copy of the given map. Convenient if
   * imported statically.
   */
  @NonNull
  public static <K, V> MapBuilder<K, V> map(final Map<K, V> source) {
    return new MapBuilder<>(source);
  }

  /**
   * Add an entry to this map, then returns {@code this}.
   *
   * @return this
   */
  public MapBuilder<K, V> entry(final K key, final V value) {
    this.map.put(key, value);
    return this;
  }

  /**
   * Return a new map based on the current state of this builder's map.
   *
   * @return A new Map<K, V> with this builder's map's current content.
   */
  public Map<K, V> build() {
    return new LinkedHashMap<>(this.map);
  }

  /* (non-Javadoc)
   * @see java.util.Map#size()
   */
  @Override
  public int size() {
    return this.map.size();
  }

  /* (non-Javadoc)
   * @see java.util.Map#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  /* (non-Javadoc)
   * @see java.util.Map#containsKey(java.lang.Object)
   */
  @Override
  public boolean containsKey(final Object key) {
    return this.map.containsKey(key);
  }

  /* (non-Javadoc)
   * @see java.util.Map#containsValue(java.lang.Object)
   */
  @Override
  public boolean containsValue(final Object value) {
    return this.map.containsValue(value);
  }

  /* (non-Javadoc)
   * @see java.util.Map#get(java.lang.Object)
   */
  @Override
  @Nullable
  public V get(final Object key) {
    return this.map.get(key);
  }

  /* (non-Javadoc)
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public V put(final K key, final V value) {
    return this.map.put(key, value);
  }

  /* (non-Javadoc)
   * @see java.util.Map#remove(java.lang.Object)
   */
  @Override
  @Nullable
  public V remove(final Object key) {
    return this.map.remove(key);
  }

  /* (non-Javadoc)
   * @see java.util.Map#putAll(java.util.Map)
   */
  @Override
  public void putAll(final Map<? extends K, ? extends V> m) {
    this.map.putAll(m);
  }

  /* (non-Javadoc)
   * @see java.util.Map#clear()
   */
  @Override
  public void clear() {
    this.map.clear();
  }

  /* (non-Javadoc)
   * @see java.util.Map#keySet()
   */
  @NonNull
  @Override
  public Set<K> keySet() {
    return this.map.keySet();
  }

  /* (non-Javadoc)
   * @see java.util.Map#values()
   */
  @NonNull
  @Override
  public Collection<V> values() {
    return this.map.values();
  }

  /* (non-Javadoc)
   * @see java.util.Map#entrySet()
   */
  @NonNull
  @Override
  public Set<Entry<K, V>> entrySet() {
    return this.map.entrySet();
  }

  /* (non-Javadoc)
   * @see java.util.Map#equals()
   */
  @Override
  public boolean equals(final Object value) {
    if (this == value) return true;
    if (value == null || this.getClass() != value.getClass()) return false;
    final MapBuilder<?, ?> that = (MapBuilder<?, ?>) value;
    return this.map.equals(that.map);
  }

  /* (non-Javadoc)
   * @see java.util.Map#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.map);
  }
}
