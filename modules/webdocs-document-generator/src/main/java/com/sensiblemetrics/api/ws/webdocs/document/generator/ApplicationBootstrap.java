package com.sensiblemetrics.api.ws.webdocs.document.generator;

import com.sensiblemetrics.api.ws.webdocs.actuator.annotation.EnableWsActuatorSecurity;
import com.sensiblemetrics.api.ws.webdocs.actuator.annotation.EnableWsApiStatus;
import com.sensiblemetrics.api.ws.webdocs.executor.annotation.EnableWsExecutor;
import com.sensiblemetrics.api.ws.webdocs.logger.annotation.EnableWsEventLogging;
import com.sensiblemetrics.api.ws.webdocs.logger.annotation.EnableWsLogging;
import com.sensiblemetrics.api.ws.webdocs.metrics.annotation.EnableWsMetrics;
import com.sensiblemetrics.api.ws.webdocs.router.annotation.EnableWsAddressing;
import com.sensiblemetrics.api.ws.webdocs.security.annotation.EnableWsEncryptableProperties;
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
@EnableWsEncryptableProperties
@EnableConfigurationProperties
@SpringBootApplication
public class ApplicationBootstrap {

  public static void main(final String[] args) {
    SpringApplication.run(ApplicationBootstrap.class, args);
  }
}
