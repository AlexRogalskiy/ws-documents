package com.sensiblemetrics.api.ws.document.generator.repository;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.QueryHint;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.hibernate.annotations.QueryHints.CACHEABLE;
import static org.hibernate.jpa.QueryHints.HINT_READONLY;

/**
 * Base {@link JpaRepository} repository declaration
 *
 * @param <E>  type of {@link Serializable} entity
 * @param <ID> type of {@link Serializable} entity identifier {@link Serializable}
 */
@NoRepositoryBean
public interface BaseRepository<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
    /**
     * Returns collection of {@link Serializable} models as {@link Stream} by query
     *
     * @param <S> type of {@link Serializable} entity
     * @return {@link Stream} of {@link Serializable} models
     */
    @Async
    @Query(RepositoryQuery.FIND_ALL)
    @QueryHints({@QueryHint(name = HINT_READONLY, value = "true"), @QueryHint(name = CACHEABLE, value = "true")})
    <S extends E> CompletableFuture<Stream<S>> streamAll();

    /**
     * Base repository entity queries
     */
    @UtilityClass
    class RepositoryQuery {
        /**
         * Default query to fetch all {@link Serializable} models
         */
        static final String FIND_ALL = "SELECT e FROM #{#entityName} e";
    }
}
