package com.sensiblemetrics.api.ws.document.generator.service.interfaces;

import com.sensiblemetrics.api.ws.commons.constraint.ConstraintGroup;
import com.sensiblemetrics.api.ws.document.generator.model.domain.FileInfo;
import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/** Document {@link BaseService} declaration */
public interface DocumentService extends BaseService<DocumentEntity, UUID> {
  /**
   * Generates document by input {@link DocumentEntity} and returns {@link FileInfo}
   *
   * @param documentEntity initial input {@link DocumentEntity} to operate by
   * @return generated document {@code FileInfo}
   */
  @Validated(ConstraintGroup.OnUpdate.class)
  FileInfo generateDocument(final DocumentEntity documentEntity);

  /**
   * Returns created {@link DocumentEntity} by input {@link DocumentEntity}
   *
   * @param documentEntity initial input {@link DocumentEntity} to operate by
   * @return created {@link DocumentEntity}
   */
  @Validated(ConstraintGroup.OnCreate.class)
  DocumentEntity createDocument(final DocumentEntity documentEntity);

  /**
   * Returns fetched {@link DocumentEntity} by input {@link DocumentEntity}
   *
   * @param documentEntity initial input {@link DocumentEntity} to operate by
   * @return fetched {@link DocumentEntity}
   */
  @Validated(ConstraintGroup.OnSelect.class)
  DocumentEntity findDocument(final DocumentEntity documentEntity);

  /**
   * Returns deleted {@link DocumentEntity} by input {@link DocumentEntity}
   *
   * @param documentEntity initial input {@link DocumentEntity} to operate by
   * @return deleted {@link DocumentEntity}
   */
  @Validated(ConstraintGroup.OnDelete.class)
  DocumentEntity deleteDocument(final DocumentEntity documentEntity);

  /**
   * Returns updated {@link DocumentEntity} by input {@link DocumentEntity}
   *
   * @param documentEntity initial input {@link DocumentEntity} to operate by
   * @return updated {@link DocumentEntity}
   */
  @Validated(ConstraintGroup.OnUpdate.class)
  DocumentEntity updateDocument(final DocumentEntity documentEntity);
}
