package com.sensiblemetrics.api.ws.commons.exception;

import com.sensiblemetrics.api.ws.commons.helper.MessageSourceHelper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;

import static com.sensiblemetrics.api.ws.commons.enumeration.ErrorTemplateType.INVALID_ENDPOINT_CONFIGURATION;

/**
 * Endpoint configuration {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EndpointConfigurationException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5120429116335637195L;

    /**
     * Initializes {@link EndpointConfigurationException} using the given {@code String} errorMessage
     *
     * @param message The message describing the exception
     */
    public EndpointConfigurationException(final String message) {
        super(message);
    }

    /**
     * Initializes {@link EndpointConfigurationException} using the given {@code Throwable} type
     *
     * @param type - initial input {@link Throwable}
     */
    public EndpointConfigurationException(final Throwable type) {
        super(type);
    }

    /**
     * Invalid mapping exception constructor with initial input {@link Throwable}
     *
     * @param message - initial input {@link String} message
     * @param type    - initial input {@link Throwable} type
     */
    public EndpointConfigurationException(final String message, final Throwable type) {
        super(message, type);
    }

    /**
     * Returns {@link EndpointConfigurationException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link EndpointConfigurationException}
     */
    @NonNull
    public static EndpointConfigurationException throwError(final String message) {
        throw new EndpointConfigurationException(message);
    }

    /**
     * Returns {@link EndpointConfigurationException} by input argument
     *
     * @param arg - initial input value {@link Object}
     * @return {@link EndpointConfigurationException}
     */
    @NonNull
    public static EndpointConfigurationException throwInvalidConfiguration(final Object arg) {
        throw throwInvalidConfigurationWith(INVALID_ENDPOINT_CONFIGURATION.getErrorMessage(), arg);
    }

    /**
     * Returns {@link ResourceNotFoundException} by input argument
     *
     * @param messageId - initial input message {@link String} identifier
     * @param arg       - initial input description {@link Object} arguments
     * @return {@link ResourceNotFoundException}
     */
    @NonNull
    public static ResourceNotFoundException throwInvalidConfigurationWith(final String messageId,
                                                                          final Object arg) {
        throw throwError(MessageSourceHelper.getMessage(messageId, arg));
    }
}
