package com.sensiblemetrics.api.ws.eureka.property;

import com.sensiblemetrics.api.ws.validation.constraint.annotation.NullOrNotEmpty;
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
import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.streamOf;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsEurekaServerProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Eureka Server configuration properties")
public class WsEurekaServerProperty {
    /**
     * Default eureka server property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "eureka-server";

    /**
     * Default {@link Map} collection of {@link ActuatorEndpoint}s
     */
    @Valid
    @NullOrNotEmpty(message = "{property.eureka.server.security.endpoints.nullOrNotEmpty}")
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
        @NotEmpty(message = "{property.eureka.server.security.endpoint.names.notEmpty}")
        private List<@NotBlank String> names;

        @Valid
        @Singular
        @NotEmpty(message = "{property.eureka.server.security.endpoint.roles.notEmpty}")
        private List<@NotBlank String> roles;

        /**
         * Enable security endpoint ({@code true} by default)
         */
        @Builder.Default
        private boolean enabled = true;

        /**
         * Return {@link String} array of names
         *
         * @return {@link String} array of names
         */
        public String[] getNamesAsArray() {
            return streamOf(this.names).toArray(String[]::new);
        }

        /**
         * Return {@link String} array of roles
         *
         * @return {@link String} array of roles
         */
        public String[] getRolesAsArray() {
            return streamOf(this.roles).toArray(String[]::new);
        }
    }

    /**
     * Enable/disable eureka server configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
