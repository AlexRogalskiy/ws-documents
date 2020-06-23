package com.sensiblemetrics.api.ws.document.generator.repository;

import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * {@link DocumentEntity} {@link BaseRepository} declaration
 */
@Repository
public interface DocumentRepository extends BaseRepository<DocumentEntity, UUID> {
}
