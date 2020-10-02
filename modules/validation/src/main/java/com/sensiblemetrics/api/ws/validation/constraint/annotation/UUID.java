package com.sensiblemetrics.api.ws.validation.constraint.annotation;

import com.sensiblemetrics.api.ws.validation.constraint.validator.UUIDValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Target({
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = UUIDValidator.class)
public @interface UUID {
    /**
     * Returns {@link String} validation message
     *
     * @return {@link String} validation message
     */
    String message() default "{com.sensiblemetrics.api.ws.validation.constraint.annotation.UUID.message}";

    /**
     * Returns {@link Class} groups array
     *
     * @return {@link Class} groups array
     */
    Class<?>[] groups() default {};

    /**
     * Returns {@link Class} array of {@link Payload}s
     *
     * @return {@link Class} array of {@link Payload}s
     */
    Class<? extends Payload>[] payload() default {};
}
