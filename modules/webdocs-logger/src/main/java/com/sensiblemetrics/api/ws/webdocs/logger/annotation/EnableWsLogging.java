package com.sensiblemetrics.api.ws.webdocs.logger.annotation;

import com.sensiblemetrics.api.ws.webdocs.logger.configuration.WsLoggingConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsLoggingConfiguration.class)
public @interface EnableWsLogging {}
