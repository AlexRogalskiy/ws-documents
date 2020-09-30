package com.sensiblemetrics.api.ws.webdocs.document.generator.modelmapper.propertymap;

import com.sensiblemetrics.api.ws.webdocs.document.generator.generated.GenerateDocumentResponse;
import com.sensiblemetrics.api.ws.webdocs.document.generator.model.domain.FileInfo;
import com.sensiblemetrics.api.ws.webdocs.document.generator.modelmapper.converter.FileInfoToOperationStatusConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * {@link FileInfo} to {@link GenerateDocumentResponse} {@link PropertyMap} binding configuration
 */
@Component
@RequiredArgsConstructor
public class FileInfoToGenerateDocumentResponsePropertyMap
    extends PropertyMap<FileInfo, GenerateDocumentResponse> {
  private final FileInfoToOperationStatusConverter fileInfoToOperationStatusConverter;

  /** {@link GenerateDocumentResponse} {@link PropertyMap} configuration */
  @Override
  protected void configure() {
    // mapping destination properties
    this.map(this.source.getDocumentId()).setId(null);
    this.map(this.source.getFileName()).setName(null);

    // mapping destination properties via converters
    this.using(this.fileInfoToOperationStatusConverter).map(this.source).setStatus(null);
  }
}
