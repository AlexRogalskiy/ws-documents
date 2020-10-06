package com.sensiblemetrics.api.ws.validation.annotation;

import com.sensiblemetrics.api.ws.validation.configuration.WsValidationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsValidationConfiguration.class)
public @interface EnableWsValidation {
}
