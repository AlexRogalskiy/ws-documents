package com.sensiblemetrics.api.ws.actuator.annotation;

import com.sensiblemetrics.api.ws.actuator.configuration.WsApiStatusConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.*;

/**
 * Conditional annotation that can be placed on {@link Configuration auto-configuration} classes in order to activate them only if {@link
 * WsApiStatusConfiguration} is enabled.
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnBean(WsApiStatusConfiguration.class)
@TypeQualifierDefault(ElementType.TYPE)
public @interface ConditionalOnWsApiStatusEnabled {
}
