package com.sensiblemetrics.api.ws.metrics.configuration;

import com.sensiblemetrics.api.ws.metrics.property.WsMetricsProperty;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

@Configuration
@ConditionalOnProperty(prefix = WsMetricsProperty.Handlers.HEALTH_STATUS_EXPORTER_PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Metrics Health Status exporter configuration")
public abstract class WsMetricsHealthStatusExporterConfiguration {

    /**
     * Metrics health status exporter configuration constructor
     *
     * @param registry       - initial input {@link MeterRegistry}
     * @param healthEndpoint - initial input {@link HealthEndpoint}
     */
    public WsMetricsHealthStatusExporterConfiguration(final MeterRegistry registry,
                                                      final HealthEndpoint healthEndpoint) {
        Gauge.builder("health", healthEndpoint, this::getStatusCode)
            .strongReference(true)
            .register(registry);
    }

    /**
     * Returns {@code int} health status code
     *
     * @param health - initial input {@link HealthEndpoint} to operate by
     * @return {@code int} health status code
     */
    private int getStatusCode(final HealthEndpoint health) {
        final Status status = health.health().getStatus();
        if (Status.UP.equals(status)) {
            return 3;
        } else if (Status.OUT_OF_SERVICE.equals(status)) {
            return 2;
        } else if (Status.DOWN.equals(status)) {
            return 1;
        }
        return 0;
    }
}
