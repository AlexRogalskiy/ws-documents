package com.sensiblemetrics.api.ws.document.uploader.service.impl;

import com.sensiblemetrics.api.ws.document.uploader.model.entity.BaseAuditEntity;
import com.sensiblemetrics.api.ws.document.uploader.repository.BaseRepository;
import com.sensiblemetrics.api.ws.document.uploader.service.interfaces.BaseService;
import com.sensiblemetrics.api.ws.document.uploader.service.interfaces.RepositoryAware;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Optional;

import static com.sensiblemetrics.api.ws.commons.exception.BadRequestException.throwBadRequest;
import static com.sensiblemetrics.api.ws.commons.exception.ResourceNotFoundException.throwResourceNotFound;
import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.copyNonNullProperties;

/**
 * {@link BaseService} implementation
 *
 * @param <E>  type of {@link BaseAuditEntity}
 * @param <ID> type of {@link BaseAuditEntity} identifier {@link Serializable}
 */
@Slf4j
@Getter(AccessLevel.PROTECTED)
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public abstract class BaseServiceImpl<E, ID extends Serializable> implements BaseService<E, ID>, RepositoryAware<E, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll(final Iterable<ID> target) {
        log.info("Fetching all target records by IDs: {}", target);
        return Optional.ofNullable(target)
                .map(value -> this.getRepository().findAllById(value))
                .orElseThrow(() -> throwResourceNotFound(target));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findById(final ID id) {
        log.info("Fetching target record by ID: {}", id);
        return Optional.ofNullable(id)
                .map(value -> this.getRepository().findById(value))
                .orElseThrow(() -> throwResourceNotFound(id));
    }

    @Override
    public E save(final E target) {
        log.info("Saving target record: {}", target);
        return Optional.ofNullable(target)
                .map(value -> this.getRepository().saveAndFlush(value))
                .orElseThrow(() -> throwBadRequest(target));
    }

    @Override
    public E update(final ID id, final E target) {
        log.info("Updating target record by ID: {}, entity: {}", id, target);
        return this.findById(id)
                .map(value -> copyNonNullProperties(target, value))
                .map(this::save)
                .orElseThrow(() -> throwResourceNotFound(id));
    }

    @Override
    public <S extends E> Iterable<S> saveAll(final Iterable<S> target) {
        log.info("Saving target records: {}", target);
        return Optional.ofNullable(target)
                .map(value -> this.getRepository().saveAll(value))
                .orElseThrow(() -> throwBadRequest(target));
    }

    @Override
    public E delete(final E target) {
        log.info("Removing target record: {}", target);
        return Optional.ofNullable(target)
                .map(value -> {
                    this.getRepository().delete(value);
                    this.getRepository().flush();
                    return value;
                })
                .orElseThrow(() -> throwBadRequest(target));
    }

    @Override
    public E deleteById(final ID id) {
        log.info("Removing target record by ID: {}", id);
        return this.findById(id)
                .map(this::delete)
                .orElseThrow(() -> throwResourceNotFound(id));
    }

    @Override
    public <S extends E> Iterable<S> deleteAll(final Iterable<S> target) {
        log.info("Removing target records: {}", target);
        return Optional.ofNullable(target)
                .map(value -> {
                    this.getRepository().deleteAll(value);
                    this.getRepository().flush();
                    return value;
                })
                .orElseThrow(() -> throwBadRequest(target));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public boolean existsById(final ID id) {
        log.info("Checking existence of target record by ID: {}", id);
        return Optional.ofNullable(id)
                .map(value -> this.getRepository().existsById(value))
                .orElseThrow(() -> throwResourceNotFound(id));
    }

    @Override
    public abstract BaseRepository<E, ID> getRepository();
}
