package com.sensiblemetrics.api.ws.document.uploader.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document.uploader.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.document.uploader.modelmapper.converter.StatusTypeToStatusConverter;
import com.sensiblemetrics.api.ws.document_uploader_web_service.Document;
import com.sensiblemetrics.api.ws.document_uploader_web_service.GetDocumentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@link DocumentEntity} to {@link Document} {@link PropertyMap} binding configuration
 */
@Component
@RequiredArgsConstructor
public class DocumentEntityToDocumentPropertyMap extends PropertyMap<DocumentEntity, Document> {
    private final StatusTypeToStatusConverter statusTypeToStatusConverter;

    /**
     * {@link GetDocumentResponse} {@link PropertyMap} configuration
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
        this.using(this.statusTypeToStatusConverter).map(this.source.getStatus()).setStatus(null);
    }
}