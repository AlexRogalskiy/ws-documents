package com.sensiblemetrics.api.ws.document.uploader.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document_uploader_web_service.GenerateDocumentRequest;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * {@link GenerateDocumentRequest} to {@link UUID} {@link PropertyMap} binding configuration
 */
@Component
public class GenerateDocumentRequestToUuidPropertyMap extends PropertyMap<GenerateDocumentRequest, UUID> {
    /**
     * {@link UUID} {@link PropertyMap} configuration
     */
    @Override
    protected void configure() {
        // mapping destination properties
        this.map(this.source.getId(), this.destination);
    }
}
