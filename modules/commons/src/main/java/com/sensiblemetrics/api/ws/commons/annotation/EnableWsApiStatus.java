package com.sensiblemetrics.api.ws.commons.annotation;

import com.sensiblemetrics.api.ws.commons.configuration.WsApiStatusConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsApiStatusConfiguration.class)
public @interface EnableWsApiStatus {
}
