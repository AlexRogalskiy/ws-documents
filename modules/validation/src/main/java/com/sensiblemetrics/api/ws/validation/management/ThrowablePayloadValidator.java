package com.sensiblemetrics.api.ws.validation.management;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

import static com.sensiblemetrics.api.ws.validation.exception.DataValidationException.throwError;
import static java.lang.String.format;
import static org.springframework.util.CollectionUtils.firstElement;

@RequiredArgsConstructor
public class ThrowablePayloadValidator implements ThrowableValidator {

    private final Validator validator;

    /**
     * {@inheritDoc}
     *
     * @see ThrowableValidator
     */
    @Override
    public <T> void validate(final T target, final Class<?>... groups) {
        final Set<ConstraintViolation<Object>> validationResult = this.validator.validate(target);
        if (!CollectionUtils.isEmpty(validationResult)) {
            throw throwError(format("Validation error; reason: {%s}", this.getFirstReason(validationResult)));
        }
    }

    /**
     * Returns {@link String} validation message by input {@link Set} collection of validation {@link Object}s
     *
     * @param validationResult - initial input {@link Set} collection of validation {@link Object}s
     * @return {@link String} validation message
     */
    private String getFirstReason(final Set<ConstraintViolation<Object>> validationResult) {
        return Optional.ofNullable(firstElement(validationResult))
            .map(ConstraintViolation::getMessage)
            .orElse(null);
    }
}
