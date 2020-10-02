package com.sensiblemetrics.api.ws.validation.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;
import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WebServiceValidationProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Validation configuration properties")
public class WebServiceValidationProperty {
    /**
     * Default validation property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "validation";

    /**
     * Default logging handlers
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.validation.handlers.notNull}")
    private Handlers handlers = new Handlers();

    /**
     * Validation handlers configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Handlers {
        /**
         * Default handlers property prefix
         */
        public static final String PROPERTY_PREFIX = WebServiceValidationProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "handlers";
        public static final String METHOD_PARAMS_PROPERTY_PREFIX = PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "method-params";

        /**
         * Default method params handler
         */
        @Valid
        @NestedConfigurationProperty
        @NotNull(message = "{property.validation.handlers.method-params.notNull}")
        private Handler methodParams = new Handler();
    }

    /**
     * Validation handler configuration properties
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
     * Enable/disable validation configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
