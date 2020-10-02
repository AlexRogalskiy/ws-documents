package com.sensiblemetrics.api.ws.validation.constraint.annotation;

import com.sensiblemetrics.api.ws.validation.constraint.validator.StateValidator;

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
@Constraint(validatedBy = StateValidator.class)
public @interface State {
    /**
     * Returns {@link String} validation message
     *
     * @return {@link String} validation message
     */
    String message() default "{com.sensiblemetrics.api.ws.validation.constraint.annotation.State.message}";

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

    /**
     * Returns {@link String} array of values
     *
     * @return array of available values
     */
    String[] value() default {};

    /**
     * Defines several {@link State} annotations on the same element.
     *
     * @see State
     */
    @Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER
    })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        /**
         * Returns {@link State} collection
         *
         * @return {@link State} collection
         */
        State[] value();
    }
}
