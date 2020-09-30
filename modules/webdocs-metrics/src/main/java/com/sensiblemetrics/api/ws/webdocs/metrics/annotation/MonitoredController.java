package com.sensiblemetrics.api.ws.webdocs.metrics.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;

import java.lang.annotation.*;

/** Monitored controller configurator annotation */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Controller
public @interface MonitoredController {
  /**
   * Returns {@link String} controller name
   *
   * @return {@link String} controller name
   */
  @AliasFor(annotation = Controller.class)
  String value() default "";
}
