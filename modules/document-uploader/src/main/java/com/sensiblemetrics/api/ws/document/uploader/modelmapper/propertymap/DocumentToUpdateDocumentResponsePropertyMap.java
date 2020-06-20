package com.sensiblemetrics.api.ws.document.uploader.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document_uploader_web_service.Document;
import com.sensiblemetrics.api.ws.document_uploader_web_service.UpdateDocumentResponse;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@link Document} to {@link UpdateDocumentResponse} {@link PropertyMap} binding configuration
 */
@Component
public class DocumentToUpdateDocumentResponsePropertyMap extends PropertyMap<Document, UpdateDocumentResponse> {
    /**
     * {@link UpdateDocumentResponse} {@link PropertyMap} configuration
     */
    @Override
    protected void configure() {
        // mapping destination properties
        this.map(this.source).setDocument(null);
    }
}
