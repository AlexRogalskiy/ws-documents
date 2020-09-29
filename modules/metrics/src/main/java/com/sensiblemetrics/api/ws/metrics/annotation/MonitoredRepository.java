package com.sensiblemetrics.api.ws.metrics.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

/** Monitored repository configurator annotation */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Repository
public @interface MonitoredRepository {
  /**
   * Returns {@link String} repository name
   *
   * @return {@link String} repository name
   */
  @AliasFor(annotation = Repository.class)
  String value() default "";
}
