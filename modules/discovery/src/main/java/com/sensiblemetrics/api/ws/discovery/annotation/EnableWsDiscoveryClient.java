package com.sensiblemetrics.api.ws.discovery.annotation;

import com.sensiblemetrics.api.ws.discovery.configuration.WebServiceDiscoveryClientConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableEurekaClient
@Import(WebServiceDiscoveryClientConfiguration.class)
public @interface EnableWsDiscoveryClient {
}
