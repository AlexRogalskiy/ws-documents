package com.sensiblemetrics.api.ws.metrics.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/** Monitored controller configurator annotation */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@RestController
public @interface MonitoredRestController {
  /**
   * Returns {@link String} REST controller name
   *
   * @return {@link String} REST controller name
   */
  @AliasFor(annotation = RestController.class)
  String value() default "";
}
