package com.sensiblemetrics.api.ws.webdocs.document.generator.service.impl;

import com.sensiblemetrics.api.ws.webdocs.document.generator.enumeration.StatusType;
import com.sensiblemetrics.api.ws.webdocs.document.generator.model.domain.FileInfo;
import com.sensiblemetrics.api.ws.webdocs.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.webdocs.document.generator.repository.DocumentRepository;
import com.sensiblemetrics.api.ws.webdocs.document.generator.service.interfaces.DocumentService;
import com.sensiblemetrics.api.ws.webdocs.document.generator.service.interfaces.DocxGeneratorService;
import com.sensiblemetrics.api.ws.webdocs.metrics.annotation.MonitoredService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.sensiblemetrics.api.ws.webdocs.commons.exception.DocumentProcessingException.throwDocumentProcessingErrorWith;
import static com.sensiblemetrics.api.ws.webdocs.commons.exception.ResourceNotFoundException.throwResourceNotFound;
import static com.sensiblemetrics.api.ws.webdocs.commons.utils.ServiceUtils.DEFAULT_COMPLETABLE_LOG_ACTION;

@Slf4j
@Getter
@MonitoredService
@RequiredArgsConstructor
public class DocumentServiceImpl extends BaseServiceImpl<DocumentEntity, UUID>
    implements DocumentService {
  private final DocumentRepository repository;
  private final DocxGeneratorService generatorService;

  /**
   * {@inheritDoc}
   *
   * @see DocumentService
   */
  @Override
  public FileInfo generateDocument(final DocumentEntity documentEntity) {
    log.info("Generating document report by document info: {}", documentEntity);

    final DocumentEntity target = this.findDocument(documentEntity);
    final Path path =
        Optional.of(target)
            .filter(data -> StatusType.isAvailable(data.getStatus()))
            .map(this.getGeneratorService()::processDocument)
            .map(data -> data.whenCompleteAsync(DEFAULT_COMPLETABLE_LOG_ACTION))
            .map(CompletableFuture::join)
            .orElseThrow(
                () ->
                    throwDocumentProcessingErrorWith(
                        "error.document.status.invalid", target.getId(), target.getStatus()));

    return this.buildFileInfo(path, target.getId());
  }

  /**
   * Returns {@link FileInfo} by input parameters
   *
   * @param path initial input {@link Path} path to operate by
   * @param documentId initial input {@link UUID} document identifier
   * @return {@link FileInfo}
   */
  public FileInfo buildFileInfo(final Path path, final UUID documentId) {
    final FileInfo.FileInfoBuilder builder = FileInfo.builder();
    builder.documentId(documentId);
    try {
      if (path.toFile().exists()) {
        builder.exists(true);
        builder.fileName(path.getFileName().toString());
      }
    } catch (Exception ex) {
      log.error("Cannot operate on file path: {}, message: {}", path, ex.getMessage());
    }
    return builder.build();
  }

  /**
   * {@inheritDoc}
   *
   * @see DocumentService
   */
  @Override
  public DocumentEntity createDocument(final DocumentEntity documentEntity) {
    return this.save(documentEntity);
  }

  /**
   * {@inheritDoc}
   *
   * @see DocumentService
   */
  @Override
  public DocumentEntity findDocument(final DocumentEntity documentEntity) {
    return this.findById(documentEntity.getId())
        .orElseThrow(() -> throwResourceNotFound(documentEntity.getId()));
  }

  /**
   * {@inheritDoc}
   *
   * @see DocumentService
   */
  @Override
  public DocumentEntity deleteDocument(final DocumentEntity documentEntity) {
    return this.deleteById(documentEntity.getId());
  }

  /**
   * {@inheritDoc}
   *
   * @see DocumentService
   */
  @Override
  public DocumentEntity updateDocument(final DocumentEntity documentEntity) {
    documentEntity.setStatus(StatusType.EDITED);
    return this.update(documentEntity.getId(), documentEntity, doc -> StatusType.isAvailable(doc.getStatus()));
  }
}
