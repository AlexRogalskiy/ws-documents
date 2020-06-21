package com.sensiblemetrics.api.ws.document.uploader.service.interfaces;

import com.sensiblemetrics.api.ws.document.uploader.model.entity.DocumentEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Document {@link BaseService} declaration
 */
public interface DocumentService extends BaseService<DocumentEntity, UUID> {
    /**
     * Returns generated document {@code byte} array by input {@link UUID} document identifier
     *
     * @param documentId initial input {@link UUID} document identifier to operate by
     * @return generated document {@code byte} array
     */
    Optional<byte[]> generateDocument(final UUID documentId);
}
