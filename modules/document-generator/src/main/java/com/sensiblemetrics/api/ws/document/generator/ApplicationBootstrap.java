package com.sensiblemetrics.api.ws.document.generator;

import com.sensiblemetrics.api.ws.commons.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableWsExecutor
@EnableWsMetrics
@EnableWsApiStatus
@EnableWsLogging
@EnableWsActuatorSecurity
@EnableWsAddressing
@EnableConfigurationProperties
@SpringBootApplication
public class ApplicationBootstrap {

    public static void main(final String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
