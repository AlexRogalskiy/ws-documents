package com.sensiblemetrics.api.ws.metrics.configuration;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Metrics Health configuration")
public abstract class WsMetricsHealthConfiguration {

    public WsMetricsHealthConfiguration(final MeterRegistry registry,
                                        final HealthEndpoint healthEndpoint) {
        Gauge.builder("health", healthEndpoint, this::getStatusCode)
                .strongReference(true)
                .register(registry);
    }

    private int getStatusCode(final HealthEndpoint health) {
        Status status = health.health().getStatus();
        if (Status.UP.equals(status)) {
            return 3;
        }
        if (Status.OUT_OF_SERVICE.equals(status)) {
            return 2;
        }
        if (Status.DOWN.equals(status)) {
            return 1;
        }
        return 0;
    }
}
