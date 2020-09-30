package com.sensiblemetrics.api.ws.webdocs.commons.exception;

import com.sensiblemetrics.api.ws.webdocs.commons.helper.MessageSourceHelper;
import com.sensiblemetrics.api.ws.webdocs.commons.enumeration.ErrorTemplateType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/** Document processing {@link RuntimeException} implementation */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DocumentProcessingException extends RuntimeException {
  /** Default explicit serialVersionUID for interoperability */
  private static final long serialVersionUID = 7884323657475908335L;

  /**
   * {@link DocumentProcessingException} constructor with initial input message
   *
   * @param message - initial input message {@link String}
   */
  public DocumentProcessingException(final String message) {
    super(message);
  }

  /**
   * {@link DocumentProcessingException} constructor with initial input target {@link Throwable}
   *
   * @param cause - initial input target {@link Throwable}
   */
  public DocumentProcessingException(final Throwable cause) {
    super(cause);
  }

  /**
   * {@link DocumentProcessingException} constructor with initial input {@link String} message and
   * {@link Throwable} target
   *
   * @param message - initial input message {@link String}
   * @param cause - initial input target {@link Throwable}
   */
  public DocumentProcessingException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Returns {@link DocumentProcessingException} by input parameters
   *
   * @param message - initial input description {@link String}
   * @return {@link DocumentProcessingException}
   */
  @NonNull
  public static DocumentProcessingException throwError(final String message) {
    throw new DocumentProcessingException(message);
  }

  /**
   * Returns {@link DocumentProcessingException} by input parameters
   *
   * @param args - initial input message arguments {@link Object}
   * @return {@link DocumentProcessingException}
   */
  @NonNull
  public static DocumentProcessingException throwDocumentProcessingError(
      @Nullable final Object... args) {
    return throwDocumentProcessingErrorWith(
        ErrorTemplateType.DOCUMENT_PROCESSING_ERROR.getErrorMessage(), args);
  }

  /**
   * Returns {@link DocumentProcessingException} by input parameters
   *
   * @param messageId - initial input message {@link String} identifier
   * @param args - initial input description {@link Object} arguments
   * @return {@link DocumentProcessingException}
   */
  @NonNull
  public static DocumentProcessingException throwDocumentProcessingErrorWith(
      final String messageId, @Nullable final Object... args) {
    throw throwError(MessageSourceHelper.getMessage(messageId, args));
  }
}
