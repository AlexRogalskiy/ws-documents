package com.sensiblemetrics.api.ws.webdocs.document.generator.endpoint;

import com.sensiblemetrics.api.ws.webdocs.document.generator.generated.*;
import com.sensiblemetrics.api.ws.webdocs.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.webdocs.document.generator.service.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

import static com.sensiblemetrics.api.ws.webdocs.commons.exception.BadRequestException.throwBadRequest;
import static com.sensiblemetrics.api.ws.webdocs.document.generator.endpoint.DocumentEndpoint.Info.*;

@Endpoint
@RequiredArgsConstructor
public class DocumentEndpoint {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @PayloadRoot(namespace = DOCUMENT_NAMESPACE_URI, localPart = FETCH_DOCUMENT_ENDPOINT)
    @ResponsePayload
    public GetDocumentResponse getDocument(@RequestPayload final GetDocumentRequest request) {
        return Optional.ofNullable(request)
                .map(data -> this.modelMapper.map(data, DocumentEntity.class))
                .map(this.documentService::findDocument)
                .map(data -> this.modelMapper.map(data, GetDocumentResponse.class))
                .orElseThrow(() -> throwBadRequest(request));
    }

    @PayloadRoot(namespace = DOCUMENT_NAMESPACE_URI, localPart = CREATE_DOCUMENT_ENDPOINT)
    @ResponsePayload
    public CreateDocumentResponse createDocument(
            @RequestPayload final CreateDocumentRequest request) {
        return Optional.ofNullable(request)
                .map(data -> this.modelMapper.map(data, DocumentEntity.class))
                .map(this.documentService::createDocument)
                .map(data -> this.modelMapper.map(data, CreateDocumentResponse.class))
                .orElseThrow(() -> throwBadRequest(request));
    }

    @PayloadRoot(namespace = DOCUMENT_NAMESPACE_URI, localPart = UPDATE_DOCUMENT_ENDPOINT)
    @ResponsePayload
    public UpdateDocumentResponse updateDocument(
            @RequestPayload final UpdateDocumentRequest request) {
        return Optional.ofNullable(request)
                .map(data -> this.modelMapper.map(data, DocumentEntity.class))
                .map(this.documentService::updateDocument)
                .map(data -> this.modelMapper.map(data, UpdateDocumentResponse.class))
                .orElseThrow(() -> throwBadRequest(request));
    }

    @PayloadRoot(namespace = DOCUMENT_NAMESPACE_URI, localPart = DELETE_DOCUMENT_ENDPOINT)
    @ResponsePayload
    public DeleteDocumentResponse deleteDocument(
            @RequestPayload final DeleteDocumentRequest request) {
        return Optional.ofNullable(request)
                .map(data -> this.modelMapper.map(data, DocumentEntity.class))
                .map(this.documentService::deleteDocument)
                .map(data -> this.modelMapper.map(data, DeleteDocumentResponse.class))
                .orElseThrow(() -> throwBadRequest(request));
    }

    @PayloadRoot(namespace = DOCUMENT_NAMESPACE_URI, localPart = GENERATE_DOCUMENT_ENDPOINT)
    @ResponsePayload
    public GenerateDocumentResponse generateDocument(
            @RequestPayload final GenerateDocumentRequest request) {
        return Optional.ofNullable(request)
                .map(data -> this.modelMapper.map(data, DocumentEntity.class))
                .map(this.documentService::generateDocument)
                .map(data -> this.modelMapper.map(data, GenerateDocumentResponse.class))
                .orElseThrow(() -> throwBadRequest(request));
    }

    @UtilityClass
    public static class Info {
        /**
         * Default namespace uri
         */
        static final String DOCUMENT_NAMESPACE_URI =
                "http://api.sensiblemetrics.com/ws/document-generator-web-service";
        /**
         * Default document endpoints
         */
        static final String CREATE_DOCUMENT_ENDPOINT = "CreateDocumentRequest";

        static final String FETCH_DOCUMENT_ENDPOINT = "GetDocumentRequest";
        static final String UPDATE_DOCUMENT_ENDPOINT = "UpdateDocumentRequest";
        static final String DELETE_DOCUMENT_ENDPOINT = "DeleteDocumentRequest";
        static final String GENERATE_DOCUMENT_ENDPOINT = "GenerateDocumentRequest";
    }
}
