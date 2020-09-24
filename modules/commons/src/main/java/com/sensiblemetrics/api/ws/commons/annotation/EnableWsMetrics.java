package com.sensiblemetrics.api.ws.commons.annotation;

import com.sensiblemetrics.api.ws.commons.configuration.WsMetricsConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Metrics configurator annotation
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsMetricsConfiguration.class)
public @interface EnableWsMetrics {
}
