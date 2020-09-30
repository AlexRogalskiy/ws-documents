package com.sensiblemetrics.api.ws.document.generator.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document.generator.generated.CreateDocumentResponse;
import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@link DocumentEntity} to {@link CreateDocumentResponse} {@link PropertyMap} binding configuration
 */
@Component
public class DocumentEntityToCreateDocumentResponsePropertyMap extends PropertyMap<DocumentEntity, CreateDocumentResponse> {
    /**
     * {@link CreateDocumentResponse} {@link PropertyMap} configuration
     */
    @Override
    protected void configure() {
        // mapping destination properties
        this.map(this.source.getId()).setId(null);
    }
}
