package com.sensiblemetrics.api.ws.logger.annotation;

import com.sensiblemetrics.api.ws.logger.handler.LogApplicationEventListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(LogApplicationEventListener.class)
public @interface EnableWsEventLogging {}
