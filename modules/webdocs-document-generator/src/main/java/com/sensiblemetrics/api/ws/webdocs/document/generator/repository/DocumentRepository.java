package com.sensiblemetrics.api.ws.webdocs.document.generator.repository;

import com.sensiblemetrics.api.ws.webdocs.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.webdocs.metrics.annotation.MonitoredRepository;

import java.util.UUID;

/** {@link DocumentEntity} {@link BaseRepository} declaration */
@MonitoredRepository
public interface DocumentRepository extends BaseRepository<DocumentEntity, UUID> {}
