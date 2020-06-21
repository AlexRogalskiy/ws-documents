package com.sensiblemetrics.api.ws.document.uploader.service.impl;

import com.sensiblemetrics.api.ws.document.uploader.model.DocumentEntity;
import com.sensiblemetrics.api.ws.document.uploader.repository.DocumentRepository;
import com.sensiblemetrics.api.ws.document.uploader.service.interfaces.DocumentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.getResultAsync;

@Slf4j
@Getter
@Service
@Validated
@RequiredArgsConstructor
public class DocumentServiceImpl extends BaseServiceImpl<DocumentEntity, UUID> implements DocumentService {
    private final DocumentRepository repository;
    private final AsyncTaskExecutor asyncTaskExecutor;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Optional<DocumentEntity> generateDocument(@NotBlank UUID documentId) {
        log.info("Generating document report by id: {}", documentId);
        return getResultAsync(this.asyncTaskExecutor, () -> this.findById(documentId));
    }
}
