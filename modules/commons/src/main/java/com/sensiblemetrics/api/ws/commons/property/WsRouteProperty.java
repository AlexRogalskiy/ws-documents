package com.sensiblemetrics.api.ws.commons.property;

import com.sensiblemetrics.api.ws.commons.constraint.NullOrNotBlank;
import com.sensiblemetrics.api.ws.commons.constraint.NullOrNotEmpty;
import com.sensiblemetrics.api.ws.commons.constraint.ResourcePathPattern;
import lombok.Data;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_DELIMITER;
import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsRouteProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons WS route configuration properties")
public class WsRouteProperty {
    /**
     * Default endpoints property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PREFIX + DEFAULT_DELIMITER + "route";

    /**
     * Default url mappings
     */
    private static final List<String> DEFAULT_URL_MAPPINGS = Collections.singletonList("/ws/*");

    /**
     * Enable/disable logging requests ({@code true} by default)
     */
    private boolean enableLoggingRequests = true;

    /**
     * Enable/disable transformation of WSDL locations ({@code true} by default)
     */
    private boolean transformWsdlLocations = true;

    /**
     * Enable/disable transformation of schema locations ({@code true} by default)
     */
    private boolean transformSchemaLocations = true;

    /**
     * Default namespace
     */
    @NullOrNotBlank(message = "{property.ws-addressing.route.namespace.nullOrNotBlank}")
    private String namespace;

    /**
     * Default url mappings
     */
    @Valid
    @NullOrNotEmpty(message = "{property.ws-addressing.route.url-mappings.nullOrNotEmpty}")
    private List<@NotBlank String> urlMappings = DEFAULT_URL_MAPPINGS;

    /**
     * Default rule entries
     */
    @Valid
    @NotEmpty(message = "{property.ws-addressing.route.endpoints.notEmpty}")
    private Map<@NotBlank String, @NotNull WsEndpoint> endpoints;

    /**
     * Web service endpoint info configuration
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class WsEndpoint {
        /**
         * Default endpoint port type name
         */
        @NullOrNotBlank(message = "{property.ws-addressing.route.endpoints.port-type-name.nullOrNotBlank}")
        private String portTypeName;

        /**
         * Default endpoint service name
         */
        @NullOrNotBlank(message = "{property.ws-addressing.route.endpoints.service-name.nullOrNotBlank}")
        private String serviceName;

        /**
         * Default endpoint location uri
         */
        @NotNull(message = "{property.ws-addressing.route.endpoints.location-uri.notNull}")
        private String locationUri;

        /**
         * Default endpoint target namespace
         */
        @NotNull(message = "{property.ws-addressing.route.endpoints.target-namespace.notNull}")
        private String targetNamespace;

        /**
         * Default endpoint resource pattern
         */
        @ResourcePathPattern(message = "{property.ws-addressing.route.endpoints.resource-pattern.pattern}")
        private String resourcePattern;

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
