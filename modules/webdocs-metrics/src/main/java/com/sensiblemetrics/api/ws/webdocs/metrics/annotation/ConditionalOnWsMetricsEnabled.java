package com.sensiblemetrics.api.ws.webdocs.metrics.annotation;

import com.sensiblemetrics.api.ws.webdocs.metrics.configuration.WsMetricsConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.*;

/**
 * Conditional annotation that can be placed on {@link Configuration auto-configuration} classes in
 * order to activate them only if {@link WsMetricsConfiguration} is enabled.
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnBean(WsMetricsConfiguration.class)
@TypeQualifierDefault(ElementType.TYPE)
public @interface ConditionalOnWsMetricsEnabled {}
