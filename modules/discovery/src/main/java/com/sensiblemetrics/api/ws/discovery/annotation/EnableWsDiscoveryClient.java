package com.sensiblemetrics.api.ws.discovery.annotation;

import com.sensiblemetrics.api.ws.discovery.configuration.WsDiscoveryClientConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableEurekaClient
@Import(WsDiscoveryClientConfiguration.class)
public @interface EnableWsDiscoveryClient {
}
