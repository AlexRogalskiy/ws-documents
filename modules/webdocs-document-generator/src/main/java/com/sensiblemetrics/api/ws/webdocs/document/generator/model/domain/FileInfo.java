package com.sensiblemetrics.api.ws.webdocs.document.generator.model.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FileInfo {
  /** Document identifier */
  private final UUID documentId;
  /** Document file name */
  private final String fileName;
  /** Document file status */
  private final boolean exists;
}
