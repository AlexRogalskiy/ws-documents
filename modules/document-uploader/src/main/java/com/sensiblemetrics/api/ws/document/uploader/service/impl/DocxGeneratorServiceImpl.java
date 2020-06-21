package com.sensiblemetrics.api.ws.document.uploader.service.impl;

import com.sensiblemetrics.api.ws.commons.exception.DocumentProcessingException;
import com.sensiblemetrics.api.ws.document.uploader.enumeration.StatusType;
import com.sensiblemetrics.api.ws.document.uploader.handler.DocumentEventHandler;
import com.sensiblemetrics.api.ws.document.uploader.model.domain.DocumentEvent;
import com.sensiblemetrics.api.ws.document.uploader.model.domain.MessageData;
import com.sensiblemetrics.api.ws.document.uploader.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.document.uploader.property.DocumentArchiveProperty;
import com.sensiblemetrics.api.ws.document.uploader.property.DocumentTemplateProperty;
import com.sensiblemetrics.api.ws.document.uploader.service.interfaces.DocxGeneratorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.findInClasspath;
import static java.lang.String.format;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class DocxGeneratorServiceImpl implements DocxGeneratorService {
    private final DocumentArchiveProperty documentArchiveProperty;
    private final DocumentTemplateProperty documentTemplateProperty;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public byte[] processDocument(final DocumentEntity documentEntity) {
        final Path filePath = this.createFilePath(documentEntity);
        this.createDocxFileFromTemplate(documentEntity, status -> this.notifyEvent(documentEntity.getId()).accept(status)).accept(filePath);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException ex) {
            throw new DocumentProcessingException(ex);
        }
    }

    private Path createFilePath(final DocumentEntity documentEntity) {
        return Paths.get(
                this.getDocumentArchiveProperty().getBasePath(),
                format("{%s}-{%s}", this.getDocumentArchiveProperty().getNamePrefix(), documentEntity.getId())
        );
    }

    private Consumer<Path> createDocxFileFromTemplate(final DocumentEntity documentEntity, final DocumentEventHandler eventHandler) {
        return path -> {
            try (final InputStream templateInputStream = findInClasspath(this.getDocumentTemplateProperty().getNamePattern()).getInputStream()) {
                eventHandler.sendEvent(StatusType.PROCESSING);

                final Set<PosixFilePermission> permissions = PosixFilePermissions.fromString(this.getDocumentArchiveProperty().getPosixFilePermissions());
                final FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);
                final Path targetFilePath = Files.createFile(path, fileAttributes);

                final WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
                final MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

                VariablePrepare.prepare(wordMLPackage);
                documentPart.variableReplace(this.buildDocumentMappings(documentEntity));

                wordMLPackage.save(targetFilePath.toFile());

                eventHandler.sendEvent(StatusType.REGISTERED);
            } catch (Exception ex) {
                log.error("ERROR: cannot generate docx file for document with id: {}, message: {}", documentEntity.getId(), ex.getMessage());
                eventHandler.sendEvent(StatusType.CANCELLED);
            }
        };
    }

    private Map<String, String> buildDocumentMappings(final DocumentEntity documentEntity) {
        final Map<String, String> variables = new HashMap<>();
        final DocumentTemplateProperty.MarkerMappings mappings = this.getDocumentTemplateProperty().getMarkerMappings();
        variables.put(mappings.getId().getName(), String.valueOf(documentEntity.getId()));
        variables.put(mappings.getCompany().getName(), documentEntity.getCompany());
        variables.put(mappings.getPartner().getName(), documentEntity.getPartner());
        variables.put(mappings.getProduct().getName(), documentEntity.getProduct());
        variables.put(mappings.getAmount().getName(), documentEntity.getAmount().toString());
        variables.put(mappings.getPrice().getName(), documentEntity.getPrice().toPlainString());
        variables.put(mappings.getData().getName(), documentEntity.getData());
        variables.put(mappings.getDate().getName(), documentEntity.getCreatedDate().toString());
        variables.put(mappings.getTotal().getName(), documentEntity.getPrice().multiply(BigDecimal.valueOf(documentEntity.getAmount())).toPlainString());
        return variables;
    }

    /**
     * Returns download status {@link Consumer} by input {@link UUID} session identifier
     *
     * @param documentId - initial input {@link UUID} document identifier
     * @return document status {@link Consumer}
     */
    private Consumer<StatusType> notifyEvent(final UUID documentId) {
        return status -> this.getEventPublisher().publishEvent(new DocumentEvent(this, MessageData.of(documentId, status)));
    }
}
