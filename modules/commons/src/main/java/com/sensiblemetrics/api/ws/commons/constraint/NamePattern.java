package com.sensiblemetrics.api.ws.commons.constraint;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.annotation.AliasFor;

import javax.annotation.meta.TypeQualifierNickname;
import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.*;

@Documented
@Target({
  ElementType.METHOD,
  ElementType.FIELD,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER,
  ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@TypeQualifierNickname
@Repeatable(NamePattern.List.class)
@ConstraintComposition(CompositionType.AND)
@NotBlank
@Length
@Pattern(regexp = NamePattern.DEFAULT_NAME_PATTERN)
public @interface NamePattern {
  /** Default file name pattern */
  String DEFAULT_NAME_PATTERN = "^[\\p{IsAlphabetic}\\p{IsDigit}\\p{IsSpace}._-]*$";

  /**
   * Returns {@code int} field max length value
   *
   * @return {@code int} field max length value
   */
  @PositiveOrZero
  @OverridesAttribute(constraint = Length.class, name = "max")
  @AliasFor(annotation = Length.class, attribute = "max")
  int maxLength() default 256;

  /**
   * Returns {@code int} field min length value
   *
   * @return {@code int} field min length value
   */
  @PositiveOrZero
  @OverridesAttribute(constraint = Length.class, name = "min")
  @AliasFor(annotation = Length.class, attribute = "min")
  int minLength() default 0;

  /**
   * Returns {@link String} field pattern value
   *
   * @return {@link String} field pattern value
   */
  @OverridesAttribute(constraint = Pattern.class, name = "regexp")
  @AliasFor(annotation = Pattern.class, attribute = "regexp")
  String pattern() default DEFAULT_NAME_PATTERN;

  /**
   * Returns array of {@link Pattern.Flag}s accepted by field pattern value
   *
   * @return array of {@link Pattern.Flag}s accepted by field pattern value
   */
  @OverridesAttribute(constraint = Pattern.class, name = "flags")
  @AliasFor(annotation = Pattern.class, attribute = "flags")
  Pattern.Flag[] patternFlags() default {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.UNICODE_CASE};

  /**
   * Returns {@link String} validation message
   *
   * @return {@link String} validation message
   */
  String message() default "{com.sensiblemetrics.api.ws.commons.constraint.NamePattern.message}";

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
   * Defines several {@link NamePattern} annotations on the same element.
   *
   * @see NamePattern
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
     * Returns {@link NamePattern} collection
     *
     * @return {@link NamePattern} collection
     */
    NamePattern[] value();
  }
}
