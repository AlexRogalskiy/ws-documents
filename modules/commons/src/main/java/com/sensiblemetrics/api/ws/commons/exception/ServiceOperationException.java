package com.sensiblemetrics.api.ws.commons.exception;

import com.sensiblemetrics.api.ws.commons.helper.MessageSourceHelper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import static com.sensiblemetrics.api.ws.commons.enumeration.ErrorTemplateType.SERVICE_OPERATION_ERROR;

/**
 * Service operation {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ServiceOperationException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8542851383807954620L;

    /**
     * {@link ServiceOperationException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public ServiceOperationException(final String message) {
        super(message);
    }

    /**
     * {@link ServiceOperationException} constructor with initial input {@link Throwable}
     *
     * @param cause - initial input cause target {@link Throwable}
     */
    public ServiceOperationException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link ServiceOperationException} constructor with initial input message and {@link Throwable}
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input cause target {@link Throwable}
     */
    public ServiceOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link ServiceOperationException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link ServiceOperationException}
     */
    @NonNull
    public static ServiceOperationException throwError(final String message) {
        throw new ServiceOperationException(message);
    }

    /**
     * Returns {@link ServiceOperationException} by input parameters
     *
     * @param message - initial input message {@link String}
     * @return {@link ServiceOperationException}
     */
    @NonNull
    public static ServiceOperationException throwInvalidOperation(final String message) {
        throw new ServiceOperationException(message);
    }

    /**
     * Returns {@link ServiceOperationException} by input parameters
     *
     * @param args - initial input description {@link Object} arguments
     * @return {@link ServiceOperationException}
     */
    @NonNull
    public static ServiceOperationException throwInvalidOperation(@Nullable final Object... args) {
        throw throwInvalidOperationWith(SERVICE_OPERATION_ERROR.getErrorMessage(), args);
    }

    /**
     * Returns {@link ServiceOperationException} by input parameters
     *
     * @param messageId - initial input message {@link String} identifier
     * @param args      - initial input description {@link Object} arguments
     * @return {@link ServiceOperationException}
     */
    @NonNull
    public static ServiceOperationException throwInvalidOperationWith(final String messageId,
                                                                      @Nullable final Object... args) {
        throw throwError(MessageSourceHelper.getMessage(messageId, args));
    }
}
