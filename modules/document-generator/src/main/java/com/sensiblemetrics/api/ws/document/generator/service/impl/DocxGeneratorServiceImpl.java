package com.sensiblemetrics.api.ws.document.generator.service.impl;

import com.sensiblemetrics.api.ws.document.generator.enumeration.StatusType;
import com.sensiblemetrics.api.ws.document.generator.handler.DocumentEventHandler;
import com.sensiblemetrics.api.ws.document.generator.model.domain.DocumentEvent;
import com.sensiblemetrics.api.ws.document.generator.model.domain.MessageData;
import com.sensiblemetrics.api.ws.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.document.generator.property.WsDocumentStorageProperty;
import com.sensiblemetrics.api.ws.document.generator.property.WsDocumentTemplateProperty;
import com.sensiblemetrics.api.ws.document.generator.service.interfaces.DocxGeneratorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.util.CastUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.findInClasspath;
import static java.lang.String.format;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class DocxGeneratorServiceImpl implements DocxGeneratorService {
    private final WsDocumentStorageProperty documentStorageProperty;
    private final WsDocumentTemplateProperty documentTemplateProperty;
    private final ConversionService conversionService;
    private final ApplicationEventPublisher eventPublisher;

    @Async
    @Override
    public CompletableFuture<Path> processDocument(final DocumentEntity documentEntity) {
        final Path filePath = this.createFilePath(documentEntity.getId());
        final DocumentEventHandler documentEventHandler = status -> this.notifyEvent(documentEntity.getId()).accept(status);
        this.createDocxFileFromTemplate(documentEntity, documentEventHandler).accept(filePath);
        return CompletableFuture.completedFuture(filePath);
    }

    /**
     * Returns full file {@link Path} by input document {@link UUID} identifier
     *
     * @param documentId initial input document {@link UUID} identifier
     * @return full file {@link Path}
     */
    private Path createFilePath(final UUID documentId) {
        return Paths.get(
                this.getDocumentStorageProperty().getBasePath(),
                format(
                        "%s%s.%s",
                        this.getDocumentStorageProperty().getFileNamePrefix(),
                        documentId,
                        this.getDocumentStorageProperty().getFileExtension()
                )
        );
    }

    /**
     * Creates new docx file by input file {@link Path} and parameters
     *
     * @param documentEntity initial input {@link DocumentEntity} to operate by
     * @param eventHandler   initial input {@link DocumentEventHandler} operator
     * @return consumer operator to create file by accepted {@link Path}
     */
    private Consumer<Path> createDocxFileFromTemplate(final DocumentEntity documentEntity, final DocumentEventHandler eventHandler) {
        return path -> {
            try (final InputStream templateInputStream = findInClasspath(this.getDocumentTemplateProperty().getNamePattern()).getInputStream()) {
                eventHandler.sendEvent(StatusType.PROCESSING);

                final WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
                final MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
                documentPart.variableReplace(this.getMappings(documentEntity));

                VariablePrepare.prepare(wordMLPackage);

                final Path targetPath = this.createNewFile(path);
                try {
                    wordMLPackage.save(targetPath.toFile());
                } catch (Exception ex) {
                    Files.delete(targetPath);
                }

                eventHandler.sendEvent(StatusType.REGISTERED);
            } catch (Exception ex) {
                log.error("ERROR: cannot generate docx file for document with id: {}, message: {}", documentEntity.getId(), ex.getMessage());
                eventHandler.sendEvent(StatusType.SUSPENDED);
            }
        };
    }

    /**
     * Returns document property {@link Map} by input {@link DocumentEntity}
     *
     * @param documentEntity initial input {@link DocumentEntity} to operate by
     * @return document property {@link Map}
     */
    private Map<String, String> getMappings(final DocumentEntity documentEntity) {
        final TypeDescriptor sourceType = TypeDescriptor.valueOf(DocumentEntity.class);
        final TypeDescriptor targetType = TypeDescriptor.map(Map.class, TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(String.class));
        return Optional.ofNullable(this.conversionService.convert(documentEntity, sourceType, targetType))
                .map(CastUtils::<Map<String, String>>cast)
                .orElseGet(Collections::emptyMap);
    }

    /**
     * Returns new file {@link Path} by input source file {@link Path}
     *
     * @param filePath initial input source file {@link Path}
     * @return new file {@link Path}
     * @throws IOException if new file path cannot be created
     */
    private Path createNewFile(final Path filePath) throws IOException {
        if (SystemUtils.IS_OS_UNIX) {
            final Set<PosixFilePermission> permissions = PosixFilePermissions.fromString(this.getDocumentStorageProperty().getPosixFilePermissions());
            final FileAttribute<Set<PosixFilePermission>> attributes = PosixFilePermissions.asFileAttribute(permissions);
            return Files.createFile(filePath, attributes);
        }
        return Files.createFile(filePath);
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
