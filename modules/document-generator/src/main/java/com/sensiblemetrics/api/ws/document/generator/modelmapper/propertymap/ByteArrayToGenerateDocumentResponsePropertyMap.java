package com.sensiblemetrics.api.ws.document.generator.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document_generator_web_service.GenerateDocumentResponse;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@code byte} array to {@link GenerateDocumentResponse} {@link PropertyMap} binding configuration
 */
@Component
public class ByteArrayToGenerateDocumentResponsePropertyMap extends PropertyMap<byte[], GenerateDocumentResponse> {
    /**
     * {@link GenerateDocumentResponse} {@link GenerateDocumentResponse} configuration
     */
    @Override
    protected void configure() {
        this.map().setValue(this.source);
    }
}
