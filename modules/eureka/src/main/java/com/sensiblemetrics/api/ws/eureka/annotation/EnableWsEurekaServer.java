package com.sensiblemetrics.api.ws.eureka.annotation;

import com.sensiblemetrics.api.ws.eureka.configuration.WsEurekaServerConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableEurekaServer
@Import(WsEurekaServerConfiguration.class)
public @interface EnableWsEurekaServer {
}
