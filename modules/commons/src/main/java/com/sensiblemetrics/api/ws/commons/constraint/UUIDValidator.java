package com.sensiblemetrics.api.ws.commons.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static java.lang.String.format;

/**
 * {@link UUID} {@link ConstraintValidator} implementation
 */
public class UUIDValidator implements ConstraintValidator<UUID, String> {

    /**
     * Default uuid pattern
     */
    public static final String DEFAULT_UUID_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    @Override
    public void initialize(final UUID constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value,
                           final ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }
        if (!value.matches(DEFAULT_UUID_PATTERN)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(format("ERROR: incorrect value format = {%s} (default pattern format = {%s})", value, DEFAULT_UUID_PATTERN)).addConstraintViolation();
            return false;
        }
        return true;
    }
}
