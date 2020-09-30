package com.sensiblemetrics.api.ws.webdocs.document.generator.service.interfaces;

import com.sensiblemetrics.api.ws.webdocs.document.generator.model.entity.DocumentEntity;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/** Document docx generator service declaration */
public interface DocxGeneratorService {
  /**
   * Processes input {@link DocumentEntity} by docx format and returns file {@link Path}
   *
   * @param documentEntity initial input {@link DocumentEntity} to operate by
   * @return generated document {@link Path}
   */
  CompletableFuture<Path> processDocument(final DocumentEntity documentEntity);
}
