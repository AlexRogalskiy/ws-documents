package com.sensiblemetrics.api.ws.document.generator;

import com.sensiblemetrics.api.ws.actuator.annotation.EnableWsActuatorSecurity;
import com.sensiblemetrics.api.ws.actuator.annotation.EnableWsApiStatus;
import com.sensiblemetrics.api.ws.admin.annotation.EnableWsAdminServer;
import com.sensiblemetrics.api.ws.executor.annotation.EnableWsExecutor;
import com.sensiblemetrics.api.ws.logger.annotation.EnableWsEventLogging;
import com.sensiblemetrics.api.ws.logger.annotation.EnableWsLogging;
import com.sensiblemetrics.api.ws.metrics.annotation.EnableWsMetrics;
import com.sensiblemetrics.api.ws.router.annotation.EnableWsAddressing;
import com.sensiblemetrics.api.ws.security.annotation.EnableWsEncryptableProperties;
import com.sensiblemetrics.api.ws.validation.annotation.EnableWsValidation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@EnableWsExecutor
@EnableWsMetrics
@EnableWsApiStatus
@EnableWsLogging
@EnableWsEventLogging
@EnableWsActuatorSecurity
@EnableWsAddressing
@EnableWsValidation
@EnableWsAdminServer
@EnableWsEncryptableProperties
@EnableConfigurationProperties
@SpringBootApplication
public class ApplicationBootstrap {

    public static void main(final String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
