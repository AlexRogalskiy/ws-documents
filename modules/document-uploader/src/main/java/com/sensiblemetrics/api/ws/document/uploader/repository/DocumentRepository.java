package com.sensiblemetrics.api.ws.document.uploader.repository;

import com.sensiblemetrics.api.ws.document.uploader.model.DocumentEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * {@link DocumentEntity} {@link BaseRepository} declaration
 */
@Repository
public interface DocumentRepository extends BaseRepository<DocumentEntity, UUID> {
}
