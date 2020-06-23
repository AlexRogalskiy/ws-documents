package com.sensiblemetrics.api.ws.commons.helper;

import com.sensiblemetrics.api.ws.commons.enumeration.ErrorTemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Configurable {@link MessageSourceAware} helper configurator
 */
@Component
@Configurable
public class MessageSourceHelper implements MessageSourceAware {
    /**
     * Default {@link MessageSource} instance holder
     */
    private static MessageSource messageSource = new DelegatingMessageSource();

    /**
     * Default {@link MessageSourceHelper} initialization by input {@link MessageSource}
     *
     * @param messageSource - initial input {@link MessageSource}
     */
    @Autowired
    public void init(final MessageSource messageSource) {
        MessageSourceHelper.messageSource = messageSource;
    }

    /**
     * Returns localized {@link String} message by input {@link String} message key, array of {@link Object} arguments and {@link Locale}
     *
     * @param messageKey - initial input {@link String} message key
     * @param arguments  - initial input array of {@link Object} arguments
     * @param locale     - initial input message {@link Locale}
     * @return localized {@link String} message
     */
    public static String getMessage(final String messageKey,
                                    @Nullable final Object[] arguments,
                                    final Locale locale) {
        return messageSource.getMessage(messageKey, arguments, locale);
    }

    /**
     * Returns localized {@link String} message by input {@link String} message key, array of {@link Object} arguments and default {@link Locale}
     *
     * @param messageKey - initial input {@link String} message key
     * @param arguments  - initial input array of {@link Object} arguments
     * @return localized {@link String} message
     */
    public static String getMessage(final String messageKey,
                                    @Nullable final Object... arguments) {
        return getMessage(messageKey, arguments, Locale.getDefault());
    }

    /**
     * Returns localized {@link String} message by input {@link ErrorTemplateType} and array of {@link Object} arguments
     *
     * @param messageTemplate - initial input {@link ErrorTemplateType}
     * @param arguments       - initial input array of {@link Object} arguments
     */
    public static String getMessage(final ErrorTemplateType messageTemplate,
                                    @Nullable final Object... arguments) {
        return getMessage(messageTemplate.getErrorMessage(), arguments);
    }

    /**
     * {@inheritDoc}
     *
     * @see MessageSourceAware
     */
    @Autowired
    @Override
    public void setMessageSource(@NonNull final MessageSource messageSource) {
        MessageSourceHelper.messageSource = messageSource;
    }
}
