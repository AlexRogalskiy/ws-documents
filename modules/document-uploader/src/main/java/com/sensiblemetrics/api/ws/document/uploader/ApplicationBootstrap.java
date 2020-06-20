package com.sensiblemetrics.api.ws.document.uploader;

import com.sensiblemetrics.api.ws.commons.annotation.EnableWsAddressing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableWsAddressing
@SpringBootApplication
@EnableConfigurationProperties
public class ApplicationBootstrap {

    public static void main(final String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
