package com.sensiblemetrics.api.ws.document.uploader.service.interfaces;

import com.sensiblemetrics.api.ws.document.uploader.model.DocumentEntity;

import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

/**
 * Document {@link BaseService} declaration
 */
public interface DocumentService extends BaseService<DocumentEntity, UUID> {
    /**
     * Returns generated {@link DocumentEntity} by input {@link UUID} document identifier
     *
     * @param documentId initial input {@link UUID} document identifier to operate by
     * @return generated {@link DocumentEntity}
     */
    Optional<DocumentEntity> generateDocument(@NotBlank final UUID documentId);
}
