package com.sensiblemetrics.api.ws.router.annotation;

import com.sensiblemetrics.api.ws.router.configuration.WsAddressingConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.ws.config.annotation.EnableWs;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableWs
@Import(WsAddressingConfiguration.class)
public @interface EnableWsAddressing {}
