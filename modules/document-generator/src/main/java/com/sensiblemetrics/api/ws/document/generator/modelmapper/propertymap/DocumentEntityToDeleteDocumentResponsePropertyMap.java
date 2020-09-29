package com.sensiblemetrics.api.ws.document.generator.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document.generator.generated.DeleteDocumentResponse;
import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.document.generator.modelmapper.converter.StatusTypeToDocumentStatusConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@link DocumentEntity} to {@link DeleteDocumentResponse} {@link PropertyMap} binding
 * configuration
 */
@Component
@RequiredArgsConstructor
public class DocumentEntityToDeleteDocumentResponsePropertyMap
    extends PropertyMap<DocumentEntity, DeleteDocumentResponse> {
  private final StatusTypeToDocumentStatusConverter statusTypeToDocumentStatusConverter;

  /** {@link DeleteDocumentResponse} {@link PropertyMap} configuration */
  @Override
  protected void configure() {
    // mapping destination properties
    this.map(this.source.getId()).getDocument().setId(null);
    this.map(this.source.getCompany()).getDocument().setCompany(null);
    this.map(this.source.getPartner()).getDocument().setPartner(null);
    this.map(this.source.getProduct()).getDocument().setProduct(null);
    this.map(this.source.getAmount()).getDocument().setAmount(null);
    this.map(this.source.getPrice()).getDocument().setPrice(null);
    this.map(this.source.getData()).getDocument().setData(null);

    // mapping destination properties via converters
    this.using(this.statusTypeToDocumentStatusConverter)
        .map(this.source.getStatus())
        .getDocument()
        .setStatus(null);
  }
}
