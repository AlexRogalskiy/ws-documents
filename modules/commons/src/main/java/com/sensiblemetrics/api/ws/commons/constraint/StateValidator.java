package com.sensiblemetrics.api.ws.commons.constraint;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.lang.String.format;

/**
 * {@link State} {@link ConstraintValidator} implementation
 */
public class StateValidator implements ConstraintValidator<State, String> {

    /**
     * Default state values
     */
    private String[] states;

    @Override
    public void initialize(final State constraintAnnotation) {
        this.states = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(final String value,
                           final ConstraintValidatorContext context) {
        if (!Sets.newHashSet(this.states).contains(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(format("ERROR: incorrect state={%s} (expected state values={%s})", value, StringUtils.join(this.states, "|"))).addConstraintViolation();
            return false;
        }
        return true;
    }
}
