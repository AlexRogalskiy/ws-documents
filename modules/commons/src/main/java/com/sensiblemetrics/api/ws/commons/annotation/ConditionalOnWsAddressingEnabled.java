package com.sensiblemetrics.api.ws.commons.annotation;

import com.sensiblemetrics.api.ws.commons.configuration.WsAddressingConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnBean(WsAddressingConfiguration.class)
@TypeQualifierDefault(ElementType.TYPE)
public @interface ConditionalOnWsAddressingEnabled {
}
