package com.sensiblemetrics.api.ws.commons.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Objects;

import static java.lang.String.format;

/**
 * {@link BigDecimalRange} {@link ConstraintValidator} implementation
 */
public class BigDecimalRangeValidator implements ConstraintValidator<BigDecimalRange, BigDecimal> {

    private long maxPrecision;
    private long minPrecision;
    private int scale;

    @Override
    public void initialize(final BigDecimalRange constraintAnnotation) {
        this.maxPrecision = constraintAnnotation.maxPrecision();
        this.minPrecision = constraintAnnotation.minPrecision();
        this.scale = constraintAnnotation.scale();
    }

    @Override
    public boolean isValid(final BigDecimal bigDecimalRangeField,
                           final ConstraintValidatorContext context) {
        if (Objects.isNull(bigDecimalRangeField)) {
            return true;
        }
        if (bigDecimalRangeField.precision() < this.minPrecision && bigDecimalRangeField.precision() > this.maxPrecision) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(format("ERROR: incorrect precision format= from {%s} to {%s} in number={%s}", this.minPrecision, this.maxPrecision, bigDecimalRangeField)).addConstraintViolation();
            return false;
        }
        if (bigDecimalRangeField.scale() > this.scale) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(format("ERROR: incorrect scale format= to {%s} in number={%s}", this.scale, bigDecimalRangeField)).addConstraintViolation();
            return false;
        }
        return true;
    }
}
