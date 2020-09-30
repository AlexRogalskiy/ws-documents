package com.sensiblemetrics.api.ws.logger.property;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
@Description("SensibleMetrics WebDocs Logging configuration properties")
public class WsLoggingProperty {
    /**
     * Default logging property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "logging";

    /**
     * Default logging handlers
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.logging.handlers.notNull}")
    private Handlers handlers = new Handlers();

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
        public static final String REPORTING_PROPERTY_PREFIX = PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "reporting";
        public static final String HEADERS_PROPERTY_PREFIX = PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "headers";

        /**
         * Default logging handler
         */
        @Valid
        @NestedConfigurationProperty
        @NotNull(message = "{property.logging.handlers.reporting.notNull}")
        private Handler reporting = new Handler();

        /**
         * Headers logging headers
         */
        @Valid
        @NestedConfigurationProperty
        @NotNull(message = "{property.logging.handlers.headers.notNull}")
        private HeadersHandler headers = new HeadersHandler();
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
     * Logging headers configuration properties
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Validated
    @Accessors(chain = true)
    public static class HeadersHandler extends Handler {
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
    }

    /**
     * Enable/disable logging configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
