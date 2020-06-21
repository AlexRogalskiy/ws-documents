package com.sensiblemetrics.api.ws.document.uploader.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document_uploader_web_service.GenerateDocumentResponse;
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
