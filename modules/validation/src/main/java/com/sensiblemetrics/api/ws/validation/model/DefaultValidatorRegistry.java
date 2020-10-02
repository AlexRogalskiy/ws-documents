package com.sensiblemetrics.api.ws.validation.model;

import org.springframework.validation.Validator;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Default {@link Validator} registry implementation
 */
public class DefaultValidatorRegistry implements ValidatorRegistry {

    private final Queue<Validator> validatorList = new ConcurrentLinkedQueue<>();

    @Override
    public void addValidator(final Validator validator) {
        this.validatorList.add(validator);
    }

    @Override
    public List<Validator> getValidatorsForObject(final Object value) {
        return this.validatorList.stream()
            .filter(i -> i.supports(value.getClass()))
            .collect(Collectors.toList());
    }
}
