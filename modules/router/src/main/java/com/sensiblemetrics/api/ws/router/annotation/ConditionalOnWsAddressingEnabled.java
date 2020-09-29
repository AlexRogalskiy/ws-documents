package com.sensiblemetrics.api.ws.router.annotation;

import com.sensiblemetrics.api.ws.router.configuration.WsAddressingConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnBean(WsAddressingConfiguration.class)
@TypeQualifierDefault(ElementType.TYPE)
public @interface ConditionalOnWsAddressingEnabled {}
