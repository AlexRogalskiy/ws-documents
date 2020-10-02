package com.sensiblemetrics.api.ws.validation.exception;

import com.sensiblemetrics.api.ws.commons.enumeration.ErrorTemplateType;
import com.sensiblemetrics.api.ws.commons.helper.MessageSourceHelper;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Data validation {@link Exception} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid data")
public class DataValidationException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5120429116335637195L;

    /**
     * Initializes {@link DataValidationException} using the given {@code String} errorMessage
     *
     * @param message The message describing the exception
     */
    public DataValidationException(final String message) {
        super(message);
    }

    /**
     * Initializes {@link DataValidationException} using the given {@code Throwable} type
     *
     * @param type - initial input {@link Throwable}
     */
    public DataValidationException(final Throwable type) {
        super(type);
    }

    /**
     * Invalid mapping exception constructor with initial input {@link Throwable}
     *
     * @param message - initial input {@link String} message
     * @param type    - initial input {@link Throwable} type
     */
    public DataValidationException(final String message, final Throwable type) {
        super(message, type);
    }

    /**
     * Returns {@link DataValidationException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link DataValidationException}
     */
    @NonNull
    public static DataValidationException throwError(final String message) {
        throw new DataValidationException(message);
    }

    /**
     * Returns {@link DataValidationException} by input argument
     *
     * @param arg - initial input value {@link Object}
     * @return {@link DataValidationException}
     */
    @NonNull
    public static DataValidationException throwInvalidData(final Object arg) {
        throw throwInvalidDataWith(ErrorTemplateType.INVALID_DATA.getErrorMessage(), arg);
    }

    /**
     * Returns {@link DataValidationException} by input argument
     *
     * @param messageId - initial input message {@link String} identifier
     * @param arg       - initial input description {@link Object} arguments
     * @return {@link DataValidationException}
     */
    @NonNull
    public static DataValidationException throwInvalidDataWith(final String messageId,
                                                               final Object arg) {
        throw throwError(MessageSourceHelper.getMessage(messageId, arg));
    }
}
