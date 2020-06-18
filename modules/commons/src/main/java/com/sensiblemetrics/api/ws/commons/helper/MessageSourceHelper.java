package com.sensiblemetrics.api.ws.commons.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Configurable {@link MessageSource} helper configurator
 */
@Component
@Configurable
public class MessageSourceHelper {
    /**
     * Default {@link MessageSource} instance holder
     */
    private static MessageSource messageSource;

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
    public static String getMessage(final String messageKey, final Object[] arguments, final Locale locale) {
        return messageSource.getMessage(messageKey, arguments, locale);
    }

    /**
     * Returns localized {@link String} message by input {@link String} message key, array of {@link Object} arguments and default {@link Locale}
     *
     * @param messageKey - initial input {@link String} message key
     * @param arguments  - initial input array of {@link Object} arguments
     * @return localized {@link String} message
     */
    public static String getMessage(final String messageKey, @Nullable final Object... arguments) {
        return messageSource.getMessage(messageKey, arguments, Locale.getDefault());
    }
}
