package com.sensiblemetrics.api.ws.webdocs.commons.exception;

import com.sensiblemetrics.api.ws.webdocs.commons.helper.MessageSourceHelper;
import com.sensiblemetrics.api.ws.webdocs.commons.enumeration.ErrorTemplateType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/** Bad request {@link RuntimeException} implementation */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BadRequestException extends RuntimeException {
  /** Default explicit serialVersionUID for interoperability */
  private static final long serialVersionUID = 8516167662034280227L;

  /**
   * {@link BadRequestException} constructor with initial input message
   *
   * @param message - initial input message {@link String}
   */
  public BadRequestException(final String message) {
    super(message);
  }

  /**
   * {@link BadRequestException} constructor with initial input target {@link Throwable}
   *
   * @param cause - initial input target {@link Throwable}
   */
  public BadRequestException(final Throwable cause) {
    super(cause);
  }

  /**
   * {@link BadRequestException} constructor with initial input {@link String} message and {@link
   * Throwable} target
   *
   * @param message - initial input message {@link String}
   * @param cause - initial input target {@link Throwable}
   */
  public BadRequestException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Returns {@link BadRequestException} by input parameters
   *
   * @param message - initial input description {@link String}
   * @return {@link BadRequestException}
   */
  @NonNull
  public static BadRequestException throwError(final String message) {
    throw new BadRequestException(message);
  }

  /**
   * Returns {@link BadRequestException} by input parameters
   *
   * @param args - initial input message arguments {@link Object}
   * @return {@link BadRequestException}
   */
  @NonNull
  public static BadRequestException throwBadRequest(@Nullable final Object... args) {
    return throwBadRequestWith(ErrorTemplateType.BAD_REQUEST.getErrorMessage(), args);
  }

  /**
   * Returns {@link BadRequestException} by input parameters
   *
   * @param messageId - initial input message {@link String} identifier
   * @param args - initial input description {@link Object} arguments
   * @return {@link BadRequestException}
   */
  @NonNull
  public static BadRequestException throwBadRequestWith(
      final String messageId, @Nullable final Object... args) {
    throw throwError(MessageSourceHelper.getMessage(messageId, args));
  }
}
