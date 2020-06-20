package com.sensiblemetrics.api.ws.document.uploader.repository;

import com.sensiblemetrics.api.ws.document_uploader_web_service.Document;
import com.sensiblemetrics.api.ws.document_uploader_web_service.Status;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class DocumentRepository {
    private static final Map<String, Document> documents = new HashMap<>();

    @PostConstruct
    public void initData() {
        final Document documentPublic = new Document();
        documentPublic.setId(UUID.randomUUID().toString());
        documentPublic.setCompany("Spain");
        documentPublic.setPartner("Madrid");
        documentPublic.setProduct("Product");
        documentPublic.setAmount(BigInteger.valueOf(46704314));
        documentPublic.setPrice(BigDecimal.valueOf(56.5));
        documentPublic.setData(new byte[]{1, 2, 3});
        documentPublic.setStatus(Status.NEW);
        documents.put(documentPublic.getId(), documentPublic);
    }

    public Document findDocument(final String name) {
        return documents.get(name);
    }
}
