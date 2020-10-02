package com.sensiblemetrics.api.ws.validation.constraint.validator;

import com.sensiblemetrics.api.ws.validation.constraint.annotation.BigDecimalRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

import static com.sensiblemetrics.api.ws.validation.constraint.utils.ConstraintUtils.validate;

/**
 * {@link BigDecimalRange} {@link ConstraintValidator} implementation
 */
public class BigDecimalRangeValidator implements ConstraintValidator<BigDecimalRange, BigDecimal> {

    /**
     * Default {@link String} message
     */
    private String message;
    /**
     * Maximum precision
     */
    private long maxPrecision;
    /**
     * Minimum precision
     */
    private long minPrecision;
    /**
     * Scale
     */
    private int scale;

    /**
     * Default {@link BigDecimal} {@link Predicate}
     */
    private final Predicate<BigDecimal> bigDecimalPredicate = value ->
        value.precision() < this.minPrecision
            || value.precision() > this.maxPrecision
            || value.scale() > this.scale;

    @Override
    public void initialize(final BigDecimalRange constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.maxPrecision = constraintAnnotation.maxPrecision();
        this.minPrecision = constraintAnnotation.minPrecision();
        this.scale = constraintAnnotation.scale();
    }

    @Override
    public boolean isValid(final BigDecimal bigDecimal,
                           final ConstraintValidatorContext context) {
        return Optional.ofNullable(bigDecimal)
            .map(value -> validate(this.bigDecimalPredicate, value, this.message, context))
            .orElse(true);
    }
}
