package com.sensiblemetrics.api.ws.document.generator.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.document_generator_web_service.CreateDocumentRequest;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@link CreateDocumentRequest} to {@link DocumentEntity} {@link PropertyMap} binding configuration
 */
@Component
public class CreateDocumentRequestToDocumentEntityPropertyMap extends PropertyMap<CreateDocumentRequest, DocumentEntity> {
    /**
     * {@link DocumentEntity} {@link PropertyMap} configuration
     */
    @Override
    protected void configure() {
        // mapping destination properties
        this.map(this.source.getCompany()).setCompany(null);
        this.map(this.source.getPartner()).setPartner(null);
        this.map(this.source.getProduct()).setProduct(null);
        this.map(this.source.getAmount()).setAmount(null);
        this.map(this.source.getPrice()).setPrice(null);
        this.map(this.source.getData()).setData(null);
    }
}
