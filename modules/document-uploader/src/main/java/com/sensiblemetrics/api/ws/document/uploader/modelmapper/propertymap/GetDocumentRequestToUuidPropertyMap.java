package com.sensiblemetrics.api.ws.document.uploader.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document_uploader_web_service.GetDocumentRequest;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * {@link GetDocumentRequest} to {@link UUID} {@link PropertyMap} binding configuration
 */
@Component
public class GetDocumentRequestToUuidPropertyMap extends PropertyMap<GetDocumentRequest, UUID> {
    /**
     * {@link UUID} {@link PropertyMap} configuration
     */
    @Override
    protected void configure() {
        // mapping destination properties
        this.map(this.source.getId(), this.destination);
    }
}
