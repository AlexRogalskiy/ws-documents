package com.sensiblemetrics.api.ws.commons.annotation;

import com.sensiblemetrics.api.ws.commons.configuration.WsAddressingConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.ws.config.annotation.EnableWs;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableWs
@Import(WsAddressingConfiguration.class)
public @interface EnableWsAddressing {
}
