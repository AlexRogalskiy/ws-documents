package com.sensiblemetrics.api.ws.webdocs.document.generator.handler;

import com.sensiblemetrics.api.ws.webdocs.document.generator.enumeration.StatusType;

/** Document event handler interface declaration */
@FunctionalInterface
public interface DocumentEventHandler {
  /**
   * Creates event by input {@link StatusType}
   *
   * @param status - initial input {@link StatusType} to propagate
   */
  void sendEvent(final StatusType status);
}
