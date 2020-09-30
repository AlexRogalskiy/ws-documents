package com.sensiblemetrics.api.ws.webdocs.commons.constraint;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.annotation.meta.TypeQualifierNickname;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.lang.annotation.*;

@Documented
@Target({
  ElementType.FIELD,
  ElementType.METHOD,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER,
  ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@TypeQualifierNickname
@ConstraintComposition(CompositionType.OR)
@Null
@NotBlank
public @interface NullOrNotBlank {
  /**
   * Default constraint message
   *
   * @return {@link String} constraint message
   */
  String message() default "{com.sensiblemetrics.api.ws.commons.constraint.NullOrNotBlank.message}";

  /**
   * Default constraint groups
   *
   * @return {@link Class} array of contrains groups
   */
  Class<?>[] groups() default {};

  /**
   * Default constraint payloads
   *
   * @return {@link Class} array of {@link Payload}s
   */
  Class<? extends Payload>[] payload() default {};
}
