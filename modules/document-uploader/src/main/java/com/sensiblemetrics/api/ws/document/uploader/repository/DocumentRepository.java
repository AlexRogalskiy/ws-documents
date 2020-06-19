package com.sensiblemetrics.api.ws.document.uploader.repository;

import io.spring.guides.gs_producing_web_service.Currency;
import io.spring.guides.gs_producing_web_service.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class DocumentRepository {
    private static final Map<String, Document> documents = new HashMap<>();

    @PostConstruct
    public void initData() {
        final Document spain = new Document();
        spain.setName("Spain");
        spain.setCapital("Madrid");
        spain.setCurrency(Currency.EUR);
        spain.setPopulation(46704314);

        documents.put(spain.getName(), spain);

        final Document poland = new Document();
        poland.setName("Poland");
        poland.setCapital("Warsaw");
        poland.setCurrency(Currency.PLN);
        poland.setPopulation(38186860);

        documents.put(poland.getName(), poland);

        final Document uk = new Document();
        uk.setName("United Kingdom");
        uk.setCapital("London");
        uk.setCurrency(Currency.GBP);
        uk.setPopulation(63705000);

        documents.put(uk.getName(), uk);
    }

    public Document findDocument(final String name) {
        Assert.notNull(name, "The Document's name must not be null");
        return documents.get(name);
    }
}
