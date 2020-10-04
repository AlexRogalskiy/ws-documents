package com.sensiblemetrics.api.ws.validation.management;

import javax.validation.groups.Default;

public interface ThrowableValidator {
    /**
     * Validates input {@link T} instance
     *
     * @param target - initial input {@link T} instance to be validated
     */
    default <T> void validate(final T target) {
        this.validate(target, Default.class);
    }

    /**
     * Validates input {@link T} instance by {@link Class} array of groups
     *
     * @param target - initial input {@link T} instance to be validated
     * @param groups - initial input {@link Class} array of groups to validate by
     */
    <T> void validate(final T target, final Class<?>... groups);
}
