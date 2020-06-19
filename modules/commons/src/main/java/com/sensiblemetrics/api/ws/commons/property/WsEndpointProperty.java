package com.sensiblemetrics.api.ws.commons.property;

import com.sensiblemetrics.api.ws.commons.constraint.NullOrNotBlank;
import com.sensiblemetrics.api.ws.commons.constraint.NullOrNotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

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
     * Default rule entries
     */
    @Valid
    @NullOrNotEmpty(message = "{property.ws-addressing.endpoints.nullOrNotEmpty}")
    private Map<@NotBlank String, @NotNull WsEndpoint> entries;

    /**
     * Web service endpoint info configuration
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class WsEndpoint {
        /**
         * Default source name
         */
        @NullOrNotBlank(message = "{property.ws-addressing.endpoints.source-address.nullOrNotBlank}")
        private String sourceAddress;

        /**
         * Enable/disable web service endpoint configuration ({@code true} by default)
         */
        private boolean enabled = true;
    }

    /**
     * Enable/disable endpoints configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
