package com.sensiblemetrics.api.ws.document.uploader.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document.uploader.model.DocumentEntity;
import com.sensiblemetrics.api.ws.document.uploader.modelmapper.converter.StatusToStatusTypeConverter;
import com.sensiblemetrics.api.ws.document_uploader_web_service.Document;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@link Document} to {@link DocumentEntity} {@link PropertyMap} binding configuration
 */
@Component
@RequiredArgsConstructor
public class DocumentToDocumentEntityPropertyMap extends PropertyMap<Document, DocumentEntity> {
    private final StatusToStatusTypeConverter statusToStatusTypeConverter;

    /**
     * {@link DocumentEntity} {@link PropertyMap} configuration
     */
    @Override
    protected void configure() {
        // mapping destination properties
        this.map(this.source.getId()).setId(null);
        this.map(this.source.getCompany()).setCompany(null);
        this.map(this.source.getPartner()).setPartner(null);
        this.map(this.source.getProduct()).setProduct(null);
        this.map(this.source.getAmount()).setAmount(null);
        this.map(this.source.getPrice()).setPrice(null);
        this.map(this.source.getData()).setData(null);

        // mapping destination properties via converters
        this.using(this.statusToStatusTypeConverter).map(this.source.getStatus()).setStatus(null);
    }
}
