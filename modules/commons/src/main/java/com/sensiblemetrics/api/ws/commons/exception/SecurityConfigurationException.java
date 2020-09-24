package com.sensiblemetrics.api.ws.commons.exception;

import com.sensiblemetrics.api.ws.commons.helper.MessageSourceHelper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import static com.sensiblemetrics.api.ws.commons.enumeration.ErrorTemplateType.INVALID_ENDPOINT_SECURITY_CONFIGURATION;

/**
 * Security {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SecurityConfigurationException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -4138614163250218571L;

    /**
     * {@link SecurityConfigurationException} constructor with initial input {@link String} message
     *
     * @param message - initial input message {@link String}
     */
    public SecurityConfigurationException(final String message) {
        super(message);
    }

    /**
     * {@link SecurityConfigurationException} constructor with initial input target {@link Throwable}
     *
     * @param cause - initial input target {@link Throwable}
     */
    public SecurityConfigurationException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link SecurityConfigurationException} constructor with initial input {@link String} message and target {@link Throwable}
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input target {@link Throwable}
     */
    public SecurityConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link SecurityConfigurationException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link SecurityConfigurationException}
     */
    @NonNull
    public static SecurityConfigurationException throwError(@Nullable final String message) {
        throw new SecurityConfigurationException(message);
    }

    /**
     * Returns {@link SecurityConfigurationException} by input parameters
     *
     * @param target - initial input value {@link Object}
     * @return {@link SecurityConfigurationException}
     */
    public static SecurityConfigurationException throwInvalidSecurityConfiguration(final Object target) {
        throw throwInvalidSecurityConfigurationWith(INVALID_ENDPOINT_SECURITY_CONFIGURATION.getErrorMessage(), target);
    }

    /**
     * Returns {@link SecurityConfigurationException} by input parameters
     *
     * @param target    - initial input value {@link Object}
     * @param throwable - initial input error {@link Throwable}
     * @return {@link SecurityConfigurationException}
     */
    public static SecurityConfigurationException throwInvalidSecurityConfiguration(final Object target, final Throwable throwable) {
        throw new SecurityConfigurationException(MessageSourceHelper.getMessage(INVALID_ENDPOINT_SECURITY_CONFIGURATION.getErrorMessage(), target), throwable);
    }

    /**
     * Returns {@link SecurityConfigurationException} by input parameters
     *
     * @param messageId - initial input message {@link String} identifier
     * @param args      - initial input description {@link Object} arguments
     * @return {@link SecurityConfigurationException}
     */
    public static SecurityConfigurationException throwInvalidSecurityConfigurationWith(final String messageId, @Nullable final Object... args) {
        throw throwError(MessageSourceHelper.getMessage(messageId, args));
    }
}
