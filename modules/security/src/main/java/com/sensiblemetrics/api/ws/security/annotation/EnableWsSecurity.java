package com.sensiblemetrics.api.ws.security.annotation;

import com.sensiblemetrics.api.ws.security.configuration.WsSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsSecurityConfiguration.class)
public @interface EnableWsSecurity {}
