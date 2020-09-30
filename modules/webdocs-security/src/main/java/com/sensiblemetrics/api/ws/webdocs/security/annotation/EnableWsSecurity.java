package com.sensiblemetrics.api.ws.webdocs.security.annotation;

import com.sensiblemetrics.api.ws.webdocs.security.configuration.WsSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsSecurityConfiguration.class)
public @interface EnableWsSecurity {}
