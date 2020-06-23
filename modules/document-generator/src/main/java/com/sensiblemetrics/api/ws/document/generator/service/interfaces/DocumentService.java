package com.sensiblemetrics.api.ws.document.generator.service.interfaces;

import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Document {@link BaseService} declaration
 */
public interface DocumentService extends BaseService<DocumentEntity, UUID> {
    /**
     * Returns generated {@link DocumentEntity} by input {@link DocumentEntity}
     *
     * @param documentInfo initial input {@link DocumentEntity} to operate by
     * @return generated document {@code byte} array
     */
    Optional<byte[]> generateDocument(final DocumentEntity documentInfo);

    /**
     * Returns fetched {@link DocumentEntity} by input {@link DocumentEntity}
     *
     * @param documentInfo initial input {@link DocumentEntity} to operate by
     * @return fetched {@link DocumentEntity}
     */
    Optional<DocumentEntity> findDocument(final DocumentEntity documentInfo);

    /**
     * Returns deleted {@link DocumentEntity} by input {@link DocumentEntity}
     *
     * @param documentInfo initial input {@link DocumentEntity} to operate by
     * @return deleted {@link DocumentEntity}
     */
    DocumentEntity deleteDocument(final DocumentEntity documentInfo);

    /**
     * Returns updated {@link DocumentEntity} by input {@link DocumentEntity}
     *
     * @param documentEntity initial input {@link DocumentEntity} to operate by
     * @return updated {@link DocumentEntity}
     */
    DocumentEntity updateDocument(final DocumentEntity documentEntity);
}
