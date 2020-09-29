package com.sensiblemetrics.api.ws.document.generator.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.document.generator.generated.GenerateDocumentRequest;
import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.document.generator.modelmapper.converter.StringToUuidConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@link GenerateDocumentRequest} to {@link DocumentEntity} {@link PropertyMap} binding
 * configuration
 */
@Component
@RequiredArgsConstructor
public class GenerateDocumentRequestToDocumentEntityPropertyMap
    extends PropertyMap<GenerateDocumentRequest, DocumentEntity> {
  private final StringToUuidConverter stringToUuidConverter;

  /** {@link DocumentEntity} {@link PropertyMap} configuration */
  @Override
  protected void configure() {
    // mapping destination properties
    using(this.stringToUuidConverter).map(this.source.getId()).setId(null);
  }
}
