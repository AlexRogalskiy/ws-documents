package com.sensiblemetrics.api.ws.document.generator.configuration;

import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Document Generator Actuator configuration")
public class WsDocumentActuatorConfiguration {

    @Bean
    @Description("Actuator meter filter configuration bean")
    public MeterFilter excludeTomcatMeterFilter() {
        return MeterFilter.denyNameStartsWith("test");
    }
}
