package com.sensiblemetrics.api.ws.document.uploader;

import com.sensiblemetrics.api.ws.commons.annotation.EnableWsAddressing;
import com.sensiblemetrics.api.ws.commons.annotation.EnableWsExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableWsExecutor
@EnableWsAddressing
@EnableConfigurationProperties
@SpringBootApplication
public class ApplicationBootstrap {

    public static void main(final String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
