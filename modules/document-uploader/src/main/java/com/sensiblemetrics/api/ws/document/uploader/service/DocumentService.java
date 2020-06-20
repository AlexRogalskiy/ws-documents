package com.sensiblemetrics.api.ws.document.uploader.service;

import io.spring.guides.gs_producing_web_service.Document;

/**
 * Document service contract declaration
 */
public interface DocumentService {

    Document findDocument(final String name);
}
