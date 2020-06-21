package com.sensiblemetrics.api.ws.document.uploader.service.impl;

import com.sensiblemetrics.api.ws.document.uploader.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.document.uploader.repository.DocumentRepository;
import com.sensiblemetrics.api.ws.document.uploader.service.interfaces.DocumentService;
import com.sensiblemetrics.api.ws.document.uploader.service.interfaces.DocxGeneratorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.getResultAsync;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl extends BaseServiceImpl<DocumentEntity, UUID> implements DocumentService {
    private final DocumentRepository repository;
    private final AsyncTaskExecutor asyncTaskExecutor;
    private final DocxGeneratorService generatorService;

    @Override
    public Optional<byte[]> generateDocument(final UUID documentId) {
        log.info("Generating document report by id: {}", documentId);
        return getResultAsync(this.getAsyncTaskExecutor(), this.createDocumentGenerator(documentId));
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
