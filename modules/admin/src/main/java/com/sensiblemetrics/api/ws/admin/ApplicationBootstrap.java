package com.sensiblemetrics.api.ws.admin;

import com.sensiblemetrics.api.ws.admin.annotation.EnableWsAdminServer;
import com.sensiblemetrics.api.ws.security.annotation.EnableWsEncryptableProperties;
import com.sensiblemetrics.api.ws.validation.annotation.EnableWsValidation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

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
