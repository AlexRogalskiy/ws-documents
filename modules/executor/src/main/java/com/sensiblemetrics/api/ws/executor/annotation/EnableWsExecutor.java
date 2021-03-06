package com.sensiblemetrics.api.ws.executor.annotation;

import com.sensiblemetrics.api.ws.executor.configuration.WsExecutorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsExecutorConfiguration.class)
public @interface EnableWsExecutor {
}
