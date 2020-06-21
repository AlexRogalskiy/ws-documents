package com.sensiblemetrics.api.ws.document.uploader.handler;

import com.sensiblemetrics.api.ws.document.uploader.model.domain.DocumentEvent;
import com.sensiblemetrics.api.ws.document.uploader.service.interfaces.DocumentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class DefaultApplicationEventListener {
    private final DocumentService documentService;

    /**
     * Handles {@link DocumentEvent} by changing current status
     *
     * @param documentEvent initial input {@link DocumentEvent} to handle
     */
    @EventListener
    public void handleDocumentEvent(final DocumentEvent documentEvent) {
        this.logEvent(documentEvent);
        this.getDocumentService().findById(documentEvent.getMessageData().getDocumentId())
                .ifPresent(document -> {
                    document.setStatus(documentEvent.getMessageData().getStatus());
                    this.getDocumentService().save(document);
                });
    }

    /**
     * Describes input {@link DocumentEvent} with logging instance
     *
     * @param event initial input {@link DocumentEvent} to log
     */
    private void logEvent(final DocumentEvent event) {
        log.info(
                "Source: [{}] >>> handling [{}]: >>> status: [{}], timestamp: [{}], message: [{}]",
                event.getSource().getClass().getSimpleName(),
                event.getClass().getSimpleName(),
                event.getMessageData().getStatus(),
                Instant.ofEpochMilli(event.getTimestamp()),
                event.getMessageData().getDocumentId()
        );
    }
}
