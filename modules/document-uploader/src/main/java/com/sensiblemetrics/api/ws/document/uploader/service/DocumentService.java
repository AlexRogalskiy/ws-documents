package com.sensiblemetrics.api.ws.document.uploader.service;

import com.sensiblemetrics.api.ws.document_uploader_web_service.Document;

/**
 * Document service contract declaration
 */
public interface DocumentService {
    /**
     * Returns {@link Document} by input {@link String} name
     *
     * @param name initial input {@link String} to fetch by
     * @return {@link Document} entity
     */
    Document findDocument(final String name);
}
