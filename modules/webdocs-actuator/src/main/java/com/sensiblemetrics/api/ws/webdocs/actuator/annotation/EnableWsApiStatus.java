package com.sensiblemetrics.api.ws.webdocs.actuator.annotation;

import com.sensiblemetrics.api.ws.webdocs.actuator.configuration.WsApiStatusConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsApiStatusConfiguration.class)
public @interface EnableWsApiStatus {}
