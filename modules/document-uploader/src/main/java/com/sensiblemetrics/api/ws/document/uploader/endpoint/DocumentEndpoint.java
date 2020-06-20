package com.sensiblemetrics.api.ws.document.uploader.endpoint;

import com.sensiblemetrics.api.ws.document.uploader.service.DocumentService;
import com.sensiblemetrics.api.ws.document_uploader_web_service.GetDocumentRequest;
import com.sensiblemetrics.api.ws.document_uploader_web_service.GetDocumentResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.sensiblemetrics.api.ws.document.uploader.endpoint.DocumentEndpoint.Info.DOCUMENT_FETCH_ENDPOINT;
import static com.sensiblemetrics.api.ws.document.uploader.endpoint.DocumentEndpoint.Info.DOCUMENT_NAMESPACE_URI;

@Endpoint
@RequiredArgsConstructor
public class DocumentEndpoint {
    private final DocumentService documentService;

    @PayloadRoot(namespace = DOCUMENT_NAMESPACE_URI, localPart = DOCUMENT_FETCH_ENDPOINT)
    @ResponsePayload
    public GetDocumentResponse getDocument(@RequestPayload final GetDocumentRequest request) {
        final GetDocumentResponse response = new GetDocumentResponse();
        response.setDocument(this.documentService.findDocument(request.getId()));
        return response;
    }

    @UtilityClass
    public static class Info {
        /**
         * Default namespace uri
         */
        static final String DOCUMENT_NAMESPACE_URI = "http://api.sensiblemetrics.com/ws/document-uploader-web-service";
        /**
         * Default fetch document endpoint
         */
        static final String DOCUMENT_FETCH_ENDPOINT = "getDocumentRequest";
    }
}
