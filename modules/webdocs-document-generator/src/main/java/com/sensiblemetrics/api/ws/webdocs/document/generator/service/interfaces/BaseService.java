package com.sensiblemetrics.api.ws.webdocs.document.generator.service.interfaces;

import java.io.Serializable;
import java.util.Optional;

/**
 * Base service declaration
 *
 * @param <E> type of entity model
 * @param <ID> type of entity model {@link Serializable} identifier
 */
public interface BaseService<E, ID extends Serializable> {
  /**
   * Returns {@link Iterable} collection of {@code E} entities by entities {@code ID} identifiers
   *
   * @param target - initial input {@link Iterable} collection of entities {@code ID}s
   * @return {@link Iterable} collection of {@code E} entities
   */
  Iterable<E> findAll(final Iterable<ID> target);

  /**
   * Returns {@code E} entity by input entity {@code ID} identifier
   *
   * @param id - initial input {@code ID} identifier
   * @return {@code E} entity
   */
  Optional<E> findById(final ID id);

  /**
   * Saves {@code E} entity to storage
   *
   * @param target - initial input {@code E} entity to save
   * @return saved {@code E} entity
   */
  E save(final E target);

  /**
   * Updates {@code E} entity by input parameters
   *
   * @param id - initial input entity {@code ID} identifier
   * @param target - initial input entity {@code E} to update
   * @return updated {@code E} entity
   */
  E update(final ID id, final E target);

  /**
   * Returns {@link Iterable} collection of {@code S} saved entities
   *
   * @param <S> type of entity model
   * @param target - initial input {@link Iterable} collection of {@code S} saved entities
   * @return {@link Iterable} collection of {@code S} saved entities
   */
  <S extends E> Iterable<S> saveAll(final Iterable<S> target);

  /**
   * Checks existence of entity in storage by input entity {@link ID} identifier
   *
   * @param id - initial input entity {@link ID} identifier
   * @return true - if entity exists, false - otherwise
   */
  boolean existsById(final ID id);

  /**
   * Removes input entity {@code E} from storage
   *
   * @param target - initial input {@code E} entity to remove
   */
  E delete(final E target);

  /**
   * Removes entity {@code E} from storage by input entity {@link ID} identifier
   *
   * @param id - initial input entity {@link ID} identifier
   * @return removed {@code E} entity
   */
  E deleteById(final ID id);

  /**
   * Removes input {@link Iterable} collection of {@code S} entities from storage
   *
   * @param <S> type of entity model
   * @param target - initial input {@link Iterable} collection of {@code S} entities
   * @return {@link Iterable} collection of removed {@code E} entities
   */
  <S extends E> Iterable<S> deleteAll(final Iterable<S> target);
}
