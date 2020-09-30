package com.sensiblemetrics.api.ws.webdocs.actuator.annotation;

import com.sensiblemetrics.api.ws.webdocs.actuator.configuration.WsActuatorSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsActuatorSecurityConfiguration.class)
public @interface EnableWsActuatorSecurity {}
