package com.sensiblemetrics.api.ws.admin.property;

import com.sensiblemetrics.api.ws.validation.constraint.annotation.NullOrNotBlank;
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
@ConfigurationProperties(prefix = WsAdminProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Admin configuration properties")
public class WsAdminProperty {
    /**
     * Default admin property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "admin";

    /**
     * Admin server url
     */
    @NullOrNotBlank(message = "{property.admin.server-url.nullOrNotBlank}")
    private String serverUrl;

    /**
     * Default admin handlers
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.admin.handlers.notNull}")
    private Handlers handlers = new Handlers();

    /**
     * Admin handlers configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Handlers {
        /**
         * Default handlers property prefix
         */
        public static final String PROPERTY_PREFIX = WsAdminProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "handlers";
        public static final String NOTIFICATION_PROPERTY_PREFIX = PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "notification";

        /**
         * Notification handler
         */
        @Valid
        @NestedConfigurationProperty
        @NotNull(message = "{property.admin.handlers.notification.notNull}")
        private Handler notification = new Handler();
    }

    /**
     * Admin handler configuration properties
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
     * Enable/disable admin configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
