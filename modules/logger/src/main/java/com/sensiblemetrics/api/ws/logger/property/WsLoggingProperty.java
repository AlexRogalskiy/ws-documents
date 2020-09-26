package com.sensiblemetrics.api.ws.logger.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;
import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsLoggingProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Logging configuration properties")
public class WsLoggingProperty {
    /**
     * Default logging property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "logging";

    /**
     * Default logging headers
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.logging.headers.notNull}")
    private Headers headers = new Headers();

    /**
     * Default logging handlers
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.logging.handlers.notNull}")
    private Handlers handlers = new Handlers();

    /**
     * Logging headers configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Headers {
        /**
         * Default handlers property prefix
         */
        public static final String PROPERTY_PREFIX = WsLoggingProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "headers";

        /**
         * Default headers
         */
        @Valid
        @NotNull(message = "{property.logging.headers.names.notNull}")
        private Set<@NotBlank String> names = Collections.emptySet();

        /**
         * Default header pattern
         */
        @NotBlank(message = "{property.logging.headers.pattern.notBlank}")
        private String pattern = ".*";

        /**
         * Enable/disable headers ({@code true} by default)
         */
        private boolean enabled = true;
    }

    /**
     * Logging handlers configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Handlers {
        /**
         * Default handlers property prefix
         */
        public static final String PROPERTY_PREFIX = WsLoggingProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "handlers";
        public static final String REPORTING_DATA_PROPERTY_PREFIX = PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "reporting-data";

        /**
         * Logging handler
         */
        @Valid
        @NestedConfigurationProperty
        @NotNull(message = "{property.logging.handlers.logging.notNull}")
        private Handler logging = new Handler();
    }

    /**
     * Logging handler configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Handler {
        /**
         * Enable/disable handler ({@code true} by default)
         */
        private boolean enabled = true;
    }

    /**
     * Enable/disable logging configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
