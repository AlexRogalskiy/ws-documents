package com.sensiblemetrics.api.ws.validation.management;

import org.springframework.validation.Validator;

import java.util.List;

/**
 * Validator registry interface declaration
 */
public interface ValidatorRegistry {
    /**
     * Adds {@link Validator} to corrent registry
     *
     * @param validator - initial input {@link Validator} to add
     */
    void addValidator(Validator validator);

    /**
     * Returns {@link List} collection of registered {@link Validator}s by input {@link Object}
     *
     * @param value - initial input {@link Object} to retrieve by
     * @return collection of registered {@link Validator}s
     */
    List<Validator> getValidatorsForObject(final Object value);
}
