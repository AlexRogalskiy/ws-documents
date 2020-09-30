package com.sensiblemetrics.api.ws.webdocs.metrics.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/** Monitored service configurator annotation */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Service
public @interface MonitoredService {
  /**
   * Returns {@link String} service name
   *
   * @return {@link String} service name
   */
  @AliasFor(annotation = Service.class)
  String value() default "";
}
