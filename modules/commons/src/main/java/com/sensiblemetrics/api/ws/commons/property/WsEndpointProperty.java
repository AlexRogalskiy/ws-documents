package com.sensiblemetrics.api.ws.commons.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_DELIMITER;
import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsEndpointProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons WS endpoint configuration properties")
public class WsEndpointProperty {
    /**
     * Default endpoints property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PREFIX + DEFAULT_DELIMITER + "endpoints";

    /**
     * Connection string to the Zookeeper cluster.
     */
    @NotBlank(message = "{property.endpoints.connection-string.notBlank}")
    private String connectionString = "localhost:2181";

    /**
     * Enable/disable endpoints configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
