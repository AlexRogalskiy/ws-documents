package com.sensiblemetrics.api.ws.document.uploader.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document.uploader.model.DocumentEntity;
import com.sensiblemetrics.api.ws.document_uploader_web_service.Document;
import com.sensiblemetrics.api.ws.document_uploader_web_service.UpdateDocumentRequest;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@link UpdateDocumentRequest} to {@link Document} {@link PropertyMap} binding configuration
 */
@Component
public class UpdateDocumentRequestToDocumentPropertyMap extends PropertyMap<UpdateDocumentRequest, Document> {
    /**
     * {@link DocumentEntity} {@link PropertyMap} configuration
     */
    @Override
    protected void configure() {
        // mapping destination properties
        this.map(this.source.getDocument(), this.destination);
    }
}
