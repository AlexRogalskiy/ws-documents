package com.sensiblemetrics.api.ws.document.generator;

import com.sensiblemetrics.api.ws.commons.annotation.EnableWsAddressing;
import com.sensiblemetrics.api.ws.commons.annotation.EnableWsExecutor;
import com.sensiblemetrics.api.ws.commons.annotation.EnableWsMetrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableWsExecutor
@EnableWsMetrics
@EnableWsAddressing
@EnableConfigurationProperties
@SpringBootApplication
public class ApplicationBootstrap {

    public static void main(final String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
