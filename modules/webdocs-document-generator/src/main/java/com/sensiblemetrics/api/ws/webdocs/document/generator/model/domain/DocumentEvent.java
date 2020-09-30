package com.sensiblemetrics.api.ws.webdocs.document.generator.model.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Document {@link ApplicationEvent} implementation */
@Getter
public class DocumentEvent extends ApplicationEvent {
  /** Default explicit serialVersionUID for interoperability */
  private static final long serialVersionUID = 6096837088881466819L;

  /** {@link MessageData} data holder */
  private final MessageData messageData;

  /**
   * {@link DocumentEvent} constructor by input parameters
   *
   * @param source - initial input source {@link Object}
   * @param messageData - initial input {@link MessageData}
   */
  public DocumentEvent(final Object source, final MessageData messageData) {
    super(source);
    this.messageData = messageData;
  }
}
