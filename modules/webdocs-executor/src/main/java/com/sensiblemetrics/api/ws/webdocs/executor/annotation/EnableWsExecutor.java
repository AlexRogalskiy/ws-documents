package com.sensiblemetrics.api.ws.webdocs.executor.annotation;

import com.sensiblemetrics.api.ws.webdocs.executor.configuration.WsExecutorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsExecutorConfiguration.class)
public @interface EnableWsExecutor {}
