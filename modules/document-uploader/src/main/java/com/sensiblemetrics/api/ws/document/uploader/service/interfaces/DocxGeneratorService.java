package com.sensiblemetrics.api.ws.document.uploader.service.interfaces;

import com.sensiblemetrics.api.ws.document.uploader.model.entity.DocumentEntity;

/**
 * Document docx generator service declaration
 */
public interface DocxGeneratorService {
    /**
     * Returns generated docx {@code byte} array by input {@link DocumentEntity}
     *
     * @param documentEntity initial input {@link DocumentEntity} to generate by
     * @return generated docx document in bytes
     */
    byte[] processDocument(final DocumentEntity documentEntity);
}
