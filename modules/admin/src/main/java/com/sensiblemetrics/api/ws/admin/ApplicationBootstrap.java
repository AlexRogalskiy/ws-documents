package com.sensiblemetrics.api.ws.admin;

import com.sensiblemetrics.api.ws.admin.annotation.EnableWsAdminServer;
import com.sensiblemetrics.api.ws.discovery.annotation.EnableWsDiscoveryClient;
import com.sensiblemetrics.api.ws.security.annotation.EnableWsEncryptableProperties;
import com.sensiblemetrics.api.ws.validation.annotation.EnableWsValidation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableWsValidation
@EnableWsAdminServer
@EnableWsDiscoveryClient
@EnableWsEncryptableProperties
@EnableConfigurationProperties
@SpringBootApplication
public class ApplicationBootstrap {

    public static void main(final String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
