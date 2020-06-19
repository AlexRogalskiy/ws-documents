package com.sensiblemetrics.api.ws.document.uploader.endpoint;

import com.sensiblemetrics.api.ws.document.uploader.repository.DocumentRepository;
import io.spring.guides.gs_producing_web_service.GetDocumentRequest;
import io.spring.guides.gs_producing_web_service.GetDocumentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class DocumentEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final DocumentRepository documentRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDocumentRequest")
    @ResponsePayload
    public GetDocumentResponse getDocument(@RequestPayload final GetDocumentRequest request) {
        final GetDocumentResponse response = new GetDocumentResponse();
        response.setDocument(this.documentRepository.findDocument(request.getName()));
        return response;
    }
}
