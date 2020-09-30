package com.sensiblemetrics.api.ws.webdocs.logger.annotation;

import com.sensiblemetrics.api.ws.webdocs.logger.handler.LogApplicationEventListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(LogApplicationEventListener.class)
public @interface EnableWsEventLogging {}
