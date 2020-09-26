package com.sensiblemetrics.api.ws.document.generator.repository;

import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.metrics.annotation.MonitoredRepository;

import java.util.UUID;

/**
 * {@link DocumentEntity} {@link BaseRepository} declaration
 */
@MonitoredRepository
public interface DocumentRepository extends BaseRepository<DocumentEntity, UUID> {
}
