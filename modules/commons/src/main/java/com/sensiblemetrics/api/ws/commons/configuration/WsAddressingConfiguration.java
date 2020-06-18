package com.sensiblemetrics.api.ws.commons.configuration;

import com.sensiblemetrics.api.ws.commons.property.WsEndpointProperty;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

@Configuration
@ConditionalOnProperty(prefix = WsEndpointProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WsEndpointProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons WS Addressing configuration")
public abstract class WsAddressingConfiguration {
    /**
     * Default configuration {@link Marker} instance
     */
    private static final Marker DEFAULT_CONFIGURATION_MARKER = MarkerFactory.getMarker("WsAddressingConfiguration");
}
