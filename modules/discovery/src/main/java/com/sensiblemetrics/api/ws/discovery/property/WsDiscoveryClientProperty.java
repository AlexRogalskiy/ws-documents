package com.sensiblemetrics.api.ws.discovery.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;
import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsDiscoveryClientProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Discovery Client configuration properties")
public class WsDiscoveryClientProperty {
    /**
     * Default discovery client property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "discovery.client";

    /**
     * Enable/disable discovery client configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
