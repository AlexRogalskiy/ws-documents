package com.sensiblemetrics.api.ws.webdocs.logger.annotation;

import org.springframework.lang.NonNull;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggingInfo {
  /**
   * Returns logging {@link Level}
   *
   * @return logging {@link Level}
   */
  Level value() default Level.VERBOSE;

  /** Level of tracing message. */
  enum Level {
    /** Brief tracing information level. */
    SUMMARY,
    /** Detailed tracing information level. */
    TRACE,
    /** Extremely detailed tracing information level. */
    VERBOSE;

    /**
     * Returns binary flag whether current {@link Level} is {@code VERBOSE}
     *
     * @return true - if current {@link Level} is {@code VERBOSE}, false - otherwise
     */
    public boolean isVerbose() {
      return this.equals(VERBOSE);
    }

    /**
     * Returns binary flag whether current {@link Level} is {@code TRACE}
     *
     * @return true - if current {@link Level} is {@code TRACE}, false - otherwise
     */
    public boolean isTrace() {
      return this.equals(TRACE);
    }

    /**
     * Returns {@link List} of all {@link Level}s
     *
     * @return {@link List} of all {@link Level}s
     */
    @NonNull
    public static List<Level> valuesList() {
      return Collections.unmodifiableList(asList(Level.values()));
    }
  }
}
