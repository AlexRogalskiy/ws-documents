package com.sensiblemetrics.api.ws.document.uploader.handler;

import com.sensiblemetrics.api.ws.document.uploader.enumeration.StatusType;

/**
 * Document event handler interface declaration
 */
@FunctionalInterface
public interface DocumentEventHandler {
    /**
     * Creates event by input {@link StatusType}
     *
     * @param status - initial input {@link StatusType} to propagate
     */
    void sendEvent(final StatusType status);
}
