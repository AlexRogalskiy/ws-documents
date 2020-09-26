package com.sensiblemetrics.api.ws.actuator.property;

import com.sensiblemetrics.api.ws.commons.constraint.NullOrNotEmpty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;
import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsActuatorSecurityProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Actuator Security configuration properties")
public class WsActuatorSecurityProperty {
    /**
     * Default actuator security property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "actuator.security";

    /**
     * Default {@link Map} collection of {@link ActuatorEndpoint}s
     */
    @Valid
    @NullOrNotEmpty(message = "{property.actuator.security.endpoints.nullOrNotEmpty}")
    private Map<@NotBlank String, @NotNull ActuatorEndpoint> endpoints;

    /**
     * Actuator security endpoint configuration properties
     */
    @Data
    @Builder
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActuatorEndpoint {
        @Valid
        @Singular
        @NotEmpty(message = "{property.actuator.security.endpoint.names.notEmpty}")
        private List<@NotBlank String> names;

        @Valid
        @Singular
        @NotEmpty(message = "{property.actuator.security.endpoint.roles.notEmpty}")
        private List<@NotBlank String> roles;

        /**
         * Enable security endpoint ({@code true} by default)
         */
        @Builder.Default
        private boolean enabled = true;
    }

    /**
     * Enable/disable actuator security configuration ({@code true} by default)
     */
    private boolean enabled = true;
}