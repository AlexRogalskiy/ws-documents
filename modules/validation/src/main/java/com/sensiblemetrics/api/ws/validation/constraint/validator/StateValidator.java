package com.sensiblemetrics.api.ws.validation.constraint.validator;

import com.sensiblemetrics.api.ws.validation.constraint.annotation.State;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static com.google.common.collect.Sets.newHashSet;
import static com.sensiblemetrics.api.ws.validation.constraint.utils.ConstraintUtils.validate;

/**
 * {@link State} {@link ConstraintValidator} implementation
 */
public class StateValidator implements ConstraintValidator<State, String> {

    /**
     * Default {@link String} message
     */
    private String message;
    /**
     * Default state values
     */
    private Set<String> states;

    /**
     * Default {@link String} {@link Predicate}
     */
    private final Predicate<String> statePredicate = value -> this.states.contains(value);

    @Override
    public void initialize(final State constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.states = newHashSet(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(final String state,
                           final ConstraintValidatorContext context) {
        return Optional.ofNullable(state)
            .map(value -> validate(this.statePredicate, value, this.message, context))
            .orElse(true);
    }
}
