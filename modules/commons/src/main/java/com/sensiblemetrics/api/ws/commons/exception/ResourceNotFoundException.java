package com.sensiblemetrics.api.ws.commons.exception;

import com.sensiblemetrics.api.ws.commons.helper.MessageSourceHelper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import static com.sensiblemetrics.api.ws.commons.enumeration.ErrorTemplateType.BAD_REQUEST;

/** Resource not found {@link RuntimeException} implementation */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResourceNotFoundException extends RuntimeException {
  /** Default explicit serialVersionUID for interoperability */
  private static final long serialVersionUID = -7771242308703561337L;

  /**
   * {@link ResourceNotFoundException} constructor with initial input message
   *
   * @param message - initial input message {@link String}
   */
  public ResourceNotFoundException(final String message) {
    super(message);
  }

  /**
   * {@link ResourceNotFoundException} constructor with initial input target {@link Throwable}
   *
   * @param cause - initial input target {@link Throwable}
   */
  public ResourceNotFoundException(final Throwable cause) {
    super(cause);
  }

  /**
   * {@link ResourceNotFoundException} constructor with initial input {@link String} message and
   * {@link Throwable} target
   *
   * @param message - initial input message {@link String}
   * @param cause - initial input target {@link Throwable}
   */
  public ResourceNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Returns {@link ResourceNotFoundException} by input parameters
   *
   * @param message - initial input description {@link String}
   * @return {@link ResourceNotFoundException}
   */
  @NonNull
  public static ResourceNotFoundException throwError(final String message) {
    throw new ResourceNotFoundException(message);
  }

  /**
   * Returns {@link ResourceNotFoundException} by input parameters
   *
   * @param args - initial input message arguments {@link Object}
   * @return {@link ResourceNotFoundException}
   */
  @NonNull
  public static ResourceNotFoundException throwResourceNotFound(@Nullable final Object... args) {
    return throwResourceNotFoundWith(BAD_REQUEST.getErrorMessage(), args);
  }

  /**
   * Returns {@link ResourceNotFoundException} by input parameters
   *
   * @param messageId - initial input message {@link String} identifier
   * @param args - initial input description {@link Object} arguments
   * @return {@link ResourceNotFoundException}
   */
  @NonNull
  public static ResourceNotFoundException throwResourceNotFoundWith(
      final String messageId, @Nullable final Object... args) {
    throw throwError(MessageSourceHelper.getMessage(messageId, args));
  }
}
