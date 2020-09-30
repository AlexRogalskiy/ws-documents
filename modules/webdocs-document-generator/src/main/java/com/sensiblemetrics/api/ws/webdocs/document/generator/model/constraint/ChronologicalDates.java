package com.sensiblemetrics.api.ws.webdocs.document.generator.model.constraint;

import org.springframework.context.annotation.Description;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Target({
  ElementType.METHOD,
  ElementType.FIELD,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER,
  ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = {ChronologicalDatesValidator.class})
@Description("Chronological dates constraint annotation")
public @interface ChronologicalDates {
  /**
   * Returns {@link String} validation message
   *
   * @return {@link String} validation message
   */
  String message() default
      "{com.sensiblemetrics.api.ws.webdocs.document.generator.model.constraint.ChronologicalDates.message}";

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
