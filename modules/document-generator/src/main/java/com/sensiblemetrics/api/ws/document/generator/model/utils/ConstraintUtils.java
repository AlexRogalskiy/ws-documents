package com.sensiblemetrics.api.ws.document.generator.model.utils;

import lombok.experimental.UtilityClass;

import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

@UtilityClass
public class ConstraintUtils {
  /**
   * Validates input {@link Predicate} by input parameters
   *
   * @param <T> type of validated value
   * @param predicate - initial input {@link Predicate} to validate by
   * @param value - initial input {@link String} to validate
   * @param message - initial input {@link String} validation message
   * @param context - initial input {@link ConstraintValidatorContext}
   * @return true - if input {@code T} value is valid, false - otherwise
   */
  public static <T> boolean validate(
      final Predicate<T> predicate,
      final T value,
      final String message,
      final ConstraintValidatorContext context) {
    if (!predicate.test(value)) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }
    return true;
  }
}
