package com.sensiblemetrics.api.ws.document.uploader.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document_uploader_web_service.DeleteDocumentRequest;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * {@link DeleteDocumentRequest} to {@link UUID} {@link PropertyMap} binding configuration
 */
@Component
public class DeleteDocumentRequestToUuidPropertyMap extends PropertyMap<DeleteDocumentRequest, UUID> {
    /**
     * {@link UUID} {@link PropertyMap} configuration
     */
    @Override
    protected void configure() {
        // mapping destination properties
        this.map(this.source.getId(), this.destination);
    }
}
