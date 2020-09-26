package com.sensiblemetrics.api.ws.metrics.annotation;

import java.lang.annotation.*;

/**
 * Monitored component configurator annotation
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MonitoredComponent {
}
