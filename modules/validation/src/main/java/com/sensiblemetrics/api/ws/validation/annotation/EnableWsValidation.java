package com.sensiblemetrics.api.ws.validation.annotation;

import com.sensiblemetrics.api.ws.validation.configuration.WebServiceValidationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebServiceValidationConfiguration.class)
public @interface EnableWsValidation {
}
