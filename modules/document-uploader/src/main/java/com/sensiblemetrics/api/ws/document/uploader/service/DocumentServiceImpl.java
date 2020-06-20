package com.sensiblemetrics.api.ws.document.uploader.service;

import com.sensiblemetrics.api.ws.document.uploader.repository.DocumentRepository;
import io.spring.guides.gs_producing_web_service.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

    @Override
    public Document findDocument(final String name) {
        return this.documentRepository.findDocument(name);
    }
}
