package com.sensiblemetrics.api.ws.document.generator.service.impl;

import com.sensiblemetrics.api.ws.document.generator.enumeration.StatusType;
import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.document.generator.repository.DocumentRepository;
import com.sensiblemetrics.api.ws.document.generator.service.interfaces.DocumentService;
import com.sensiblemetrics.api.ws.document.generator.service.interfaces.DocxGeneratorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static com.sensiblemetrics.api.ws.commons.exception.DocumentProcessingException.throwDocumentProcessingErrorWith;
import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.getResultAsync;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl extends BaseServiceImpl<DocumentEntity, UUID> implements DocumentService {
    private final DocumentRepository repository;
    private final AsyncTaskExecutor asyncTaskExecutor;
    private final DocxGeneratorService generatorService;

    /**
     * {@inheritDoc}
     *
     * @see DocumentService
     */
    @Override
    public Optional<byte[]> generateDocument(final DocumentEntity documentEntity) {
        log.info("Generating document report by document info: {}", documentEntity);
        if (!documentEntity.getStatus().isActive()) {
            throw throwDocumentProcessingErrorWith("error.document.status.invalid", documentEntity);
        }
        return getResultAsync(this.getAsyncTaskExecutor(), this.createDocumentGenerator(documentEntity.getId()));
    }

    /**
     * {@inheritDoc}
     *
     * @see DocumentService
     */
    @Override
    public Optional<DocumentEntity> findDocument(final DocumentEntity documentEntity) {
        return this.findById(documentEntity.getId());
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
        return this.update(documentEntity.getId(), documentEntity, d -> d.getStatus().isActive());
    }

    /**
     * Returns source document {@link Supplier} by input {@link UUID} document identifier
     *
     * @param documentId initial input {@link UUID} document identifier to fetch by
     * @return source document {@link Supplier}
     */
    private Supplier<Optional<byte[]>> createDocumentGenerator(final UUID documentId) {
        return () -> this.findById(documentId)
                .map(this.getGeneratorService()::processDocument);
    }
}
